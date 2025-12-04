package ru.daniil4jk.strongram.core.dialog.state;

import ru.daniil4jk.strongram.core.storage.Storage;

public interface DialogContext<ENUM extends Enum<ENUM>> {
    Storage getStorage();
    void moveToState(ENUM fromState, ENUM toState);
    void setState(ENUM toState);
    ENUM getState();
    void stop();
    boolean isStopped();
}
