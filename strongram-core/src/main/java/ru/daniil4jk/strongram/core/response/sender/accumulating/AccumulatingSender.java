package ru.daniil4jk.strongram.core.response.sender.accumulating;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.daniil4jk.strongram.core.response.entity.Response;
import ru.daniil4jk.strongram.core.response.sender.AbstractSender;

import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode(callSuper = true)
public class AccumulatingSender extends AbstractSender implements Accumulating {
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
