package ru.daniil4jk.strongram.core.chain.handler.preinstalled.dialog;

import ru.daniil4jk.strongram.core.chain.context.TelegramUUID;
import ru.daniil4jk.strongram.core.dialog.Dialog;

import java.util.List;

public interface DialogStorage {
    List<Dialog> get(TelegramUUID uuid);
    void remove(TelegramUUID uuid, Dialog dialog);
    void addAll(TelegramUUID uuid, List<Dialog> dialogs);
}
