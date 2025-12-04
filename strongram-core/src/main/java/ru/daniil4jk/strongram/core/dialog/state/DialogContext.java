package ru.daniil4jk.strongram.core.dialog.state;

import ru.daniil4jk.strongram.core.storage.Storage;

public interface DialogContext<ENUM extends Enum<ENUM>> {
    Storage getStorage();
    void moveToState(ENUM fromState, ENUM toState);
    ENUM getState();
}
