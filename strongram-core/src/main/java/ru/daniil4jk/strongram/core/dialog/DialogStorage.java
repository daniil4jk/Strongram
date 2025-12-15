package ru.daniil4jk.strongram.core.dialog;

import ru.daniil4jk.strongram.core.context.request.TelegramUUID;

import java.util.List;

public interface DialogStorage {
    List<Dialog> get(TelegramUUID uuid);
    void remove(TelegramUUID uuid, Dialog dialog);
    void addAll(TelegramUUID uuid, List<Dialog> dialogs);
}
