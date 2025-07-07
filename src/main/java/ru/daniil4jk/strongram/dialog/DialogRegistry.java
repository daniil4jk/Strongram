package ru.daniil4jk.strongram.dialog;

import ru.daniil4jk.strongram.TelegramUUID;

import java.util.List;

public interface DialogRegistry {
    boolean addByUUID(TelegramUUID uuid, Dialog dialog);
    List<Dialog> getAllByUUID(TelegramUUID uuid);
    boolean contains(TelegramUUID uuid, Dialog dialog);
    boolean remove(TelegramUUID uuid, Dialog dialog);
}
