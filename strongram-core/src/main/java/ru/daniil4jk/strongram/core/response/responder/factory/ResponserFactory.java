package ru.daniil4jk.strongram.core.response.responder.factory;

import ru.daniil4jk.strongram.core.response.responder.accumulator.AccumulatorResponder;
import ru.daniil4jk.strongram.core.response.sender.ResponseSink;

public interface ResponserFactory {
    void setTempCallback(ResponseSink callback);
    void setPermanentCallback(ResponseSink callback);
    AccumulatorResponder create();
}
