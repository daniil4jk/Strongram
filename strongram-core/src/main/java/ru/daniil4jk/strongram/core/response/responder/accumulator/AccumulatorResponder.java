package ru.daniil4jk.strongram.core.response.responder.accumulator;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.daniil4jk.strongram.core.response.dto.Response;
import ru.daniil4jk.strongram.core.response.responder.AbstractResponder;
import ru.daniil4jk.strongram.core.response.sender.ResponseSink;

import java.util.ArrayList;

@ToString
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class AccumulatorResponder extends AbstractResponder implements AutoCloseable {
    private final ArrayList<Response<?>> responses = new ArrayList<>();
    private final ResponseSink sendMethod;

    @Override
    protected <T extends Response<?>> void sendInternal(T response) {
        responses.add(response);
    }

    @Override
    public void close() {
        sendMethod.accept(responses);
    }
}
