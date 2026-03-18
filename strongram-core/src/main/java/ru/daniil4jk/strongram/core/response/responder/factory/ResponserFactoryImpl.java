package ru.daniil4jk.strongram.core.response.responder.factory;

import ru.daniil4jk.strongram.core.response.responder.accumulator.AccumulatorResponder;
import ru.daniil4jk.strongram.core.response.sender.ResponseSink;

public class ResponserFactoryImpl implements ResponserFactory {
    private final ThreadLocal<ResponseSink> tempCallback = new ThreadLocal<>();
    private boolean isTempCallbackUsed = false;
    private ResponseSink permanentCallback;

    public void setTempCallback(ResponseSink callback) {
        if (callback != null) {
            isTempCallbackUsed = true;
            tempCallback.set(callback);
        }
    }

    public void resetTempCallback() {
        if (isTempCallbackUsed) {
            tempCallback.remove();
        }
    }

    @Override
    public void setPermanentCallback(ResponseSink callback) {
        permanentCallback = callback;
    }

    public ResponseSink getCurrentCallback() {
        var tempCallbackCheckpoint = tempCallback.get();
        return tempCallbackCheckpoint != null ? tempCallbackCheckpoint : permanentCallback;
    }

    @Override
    public AccumulatorResponder create() {
        return new AccumulatorResponder(getCurrentCallback());
    }
}
