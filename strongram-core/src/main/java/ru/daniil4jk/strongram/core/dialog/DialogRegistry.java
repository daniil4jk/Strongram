package ru.daniil4jk.strongram.core.dialog;

import ru.daniil4jk.strongram.core.TelegramUUID;

import java.util.List;

public interface DialogRegistry {
    boolean addByUUID(TelegramUUID uuid, Dialog dialog);

    List<Dialog> getAllByUUID(TelegramUUID uuid);

    boolean contains(TelegramUUID uuid, Dialog dialog);

    boolean remove(TelegramUUID uuid, Dialog dialog);
}
