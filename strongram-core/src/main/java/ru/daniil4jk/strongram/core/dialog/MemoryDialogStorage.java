package ru.daniil4jk.strongram.core.dialog;

import ru.daniil4jk.strongram.core.context.request.TelegramUUID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryDialogStorage implements DialogStorage {
    private final Map<TelegramUUID, List<Dialog>> storage = new HashMap<>();

    @Override
    public List<Dialog> get(TelegramUUID uuid) {
        return storage.get(uuid);
    }

    @Override
    public void remove(TelegramUUID uuid, Dialog dialog) {
        storage.compute(uuid,
                (u, dialogs) -> {
                    if (dialogs == null) {
                        throw new IllegalStateException("попытка удалить диалог у пользователя с несуществующим списком диалогов");
                    }
                    dialogs.remove(dialog);
                    if (dialogs.isEmpty()) {
                        return null;
                    }
                    return dialogs;
                }
        );
    }

    @Override
    public void addAll(TelegramUUID uuid, List<Dialog> newDialogs) {
        storage.computeIfAbsent(uuid, u -> new ArrayList<>())
                .addAll(newDialogs);
    }
}
