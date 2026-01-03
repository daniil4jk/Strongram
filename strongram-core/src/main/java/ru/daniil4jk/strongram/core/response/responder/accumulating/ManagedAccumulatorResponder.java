package ru.daniil4jk.strongram.core.response.responder.accumulating;

import ru.daniil4jk.strongram.core.response.dto.Response;

import java.util.concurrent.atomic.AtomicBoolean;

public class ManagedAccumulatorResponder extends AccumulatorResponder implements AutoCloseable {
    private final AtomicBoolean expired = new AtomicBoolean(false);

    @Override
    protected <T extends Response<?>> void sendInternal(T response) {
        if (expired.get()) {
            throw new IllegalArgumentException("Can`t use expired accumulating sender");
        }
        super.sendInternal(response);
    }

    @Override
    public void close() {
        expired.set(true);
    }
}
