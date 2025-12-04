package ru.daniil4jk.strongram.core.dialog.state;

import ru.daniil4jk.strongram.core.storage.Storage;
import ru.daniil4jk.strongram.core.storage.StorageImpl;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class DialogContextImpl<ENUM extends Enum<ENUM>> implements DialogContext<ENUM> {
    private final Storage storage = new StorageImpl();
    private final AtomicReference<ENUM> state;

    public DialogContextImpl(ENUM initState) {
        state = new AtomicReference<>(initState);
    }

    @Override
    public Storage getStorage() {
        return storage;
    }

    @Override
    public void moveToState(ENUM fromState, ENUM toState) {
        ENUM expectedFromState = state.compareAndExchange(fromState, toState);
        boolean successful = Objects.equals(fromState, expectedFromState);
        if (!successful) {
            throw new IllegalStateException(
                    "can`t move dialog from state %s to state %s because dialog currently at state %s"
                            .formatted(fromState, toState, expectedFromState)
            );
        }
    }

    @Override
    public ENUM getState() {
        return state.get();
    }
}
