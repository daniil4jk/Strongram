package ru.daniil4jk.strongram.core.context.dialog;

import ru.daniil4jk.strongram.core.context.storage.Storage;

public interface DialogContext<ENUM extends Enum<ENUM>> {
    Storage getStorage();
    void moveToState(ENUM fromState, ENUM toState);
    void setState(ENUM toState);
    ENUM getState();
    void stop();
    boolean isStopped();
}
