package ru.daniil4jk.strongram.core.context.dialog;

import ru.daniil4jk.strongram.core.context.storage.InMemoryStorage;
import ru.daniil4jk.strongram.core.context.storage.Storage;

import java.util.concurrent.atomic.AtomicReference;

public class DialogContextImpl<ENUM extends Enum<ENUM>> implements DialogContext<ENUM> {
    private final Storage storage;
    private final AtomicReference<ENUM> state;
    private volatile boolean stopped = false;

    public DialogContextImpl(ENUM initState) {
        this(initState, null);
    }

    public DialogContextImpl(ENUM initState, Storage storage) {
        this.state = new AtomicReference<>(initState);
        if (storage == null) {
            storage = new InMemoryStorage();
        }
        this.storage = storage;
    }

    @Override
    public Storage getDialogScopeStorage() {
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
