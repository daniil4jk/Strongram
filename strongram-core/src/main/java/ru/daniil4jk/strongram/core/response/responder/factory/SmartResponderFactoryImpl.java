package ru.daniil4jk.strongram.core.response.responder.factory;

import lombok.RequiredArgsConstructor;
import ru.daniil4jk.strongram.core.context.request.TelegramUUID;
import ru.daniil4jk.strongram.core.response.responder.sink.SinkResponder;
import ru.daniil4jk.strongram.core.response.responder.smart.SmartResponder;
import ru.daniil4jk.strongram.core.response.responder.smart.SmartResponderImpl;
import ru.daniil4jk.strongram.core.response.sender.ResponseSink;

@RequiredArgsConstructor
public class SmartResponderFactoryImpl implements SmartResponderFactory {
    private final ResponserFactory inherit;

    @Override
    public void setTempCallback(ResponseSink callback) {
        inherit.setTempCallback(callback);
    }

    @Override
    public void resetTempCallback() {
        inherit.resetTempCallback();
    }

    @Override
    public void setPermanentCallback(ResponseSink callback) {
        inherit.setPermanentCallback(callback);
    }

    @Override
    public SinkResponder create() {
        return inherit.create();
    }

    @Override
    public SmartResponder createSmart(TelegramUUID uuid) {
        return new SmartResponderImpl(uuid, create());
    }
}
