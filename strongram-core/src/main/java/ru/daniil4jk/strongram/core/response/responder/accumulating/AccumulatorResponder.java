package ru.daniil4jk.strongram.core.response.responder.accumulating;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.daniil4jk.strongram.core.response.dto.Response;
import ru.daniil4jk.strongram.core.response.responder.AbstractResponder;

import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode(callSuper = true)
public class AccumulatorResponder extends AbstractResponder implements Accumulator {
    private final List<Response<?>> responses = new ArrayList<>();

    @Override
    protected <T extends Response<?>> void sendInternal(T response) {
        responses.add(response);
    }

    @Override
    public List<Response<?>> getQueuedMessages() {
        return responses;
    }
}
