package ru.daniil4jk.strongram.core.response.responder.sink;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.daniil4jk.strongram.core.response.dto.Response;
import ru.daniil4jk.strongram.core.response.responder.AbstractResponder;
import ru.daniil4jk.strongram.core.response.sender.ResponseSink;

@ToString
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class SinkResponder extends AbstractResponder {
    private final ResponseSink sendMethod;

    @Override
    protected <T extends Response<?>> void sendInternal(T response) {
        sendMethod.accept(response);
    }
}
