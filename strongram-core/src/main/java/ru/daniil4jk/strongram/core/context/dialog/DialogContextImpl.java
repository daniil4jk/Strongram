package ru.daniil4jk.strongram.core.context.dialog;

import ru.daniil4jk.strongram.core.context.storage.Storage;
import ru.daniil4jk.strongram.core.context.storage.StorageImpl;

import java.util.concurrent.atomic.AtomicReference;

public class DialogContextImpl<ENUM extends Enum<ENUM>> implements DialogContext<ENUM> {
    private final Storage storage = new StorageImpl();
    private final AtomicReference<ENUM> state;
    private volatile boolean stopped = false;

    public DialogContextImpl(ENUM initState) {
        state = new AtomicReference<>(initState);
    }

    @Override
    public Storage getStorage() {
        return storage;
    }

    @Override
    public void setState(ENUM toState) {
        state.set(toState);
    }

    @Override
    public ENUM getState() {
        return state.get();
    }

    @Override
    public void stop() {
        stopped = true;
    }

    @Override
    public boolean isStopped() {
        return stopped;
    }
}
