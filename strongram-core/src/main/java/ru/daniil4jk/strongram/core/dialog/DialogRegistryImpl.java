package ru.daniil4jk.strongram.core.dialog;

import ru.daniil4jk.strongram.core.TelegramUUID;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DialogRegistryImpl implements DialogRegistry {
    private final Map<TelegramUUID, List<Dialog>> map = new ConcurrentHashMap<>();

    @Override
    public boolean addByUUID(TelegramUUID uuid, Dialog dialog) {
        var list = map.computeIfAbsent(uuid, id -> new ArrayList<>());
        if (list.contains(dialog)) return false;
        list.add(dialog);
        return true;
    }

    @Override
    public List<Dialog> getAllByUUID(TelegramUUID uuid) {
        return map.get(uuid) != null ? map.get(uuid) : Collections.emptyList();
    }

    @Override
    public boolean contains(TelegramUUID uuid, Dialog dialog) {
        return map.containsKey(uuid) && map.get(uuid).contains(dialog);
    }

    @Override
    public boolean remove(TelegramUUID uuid, Dialog dialog) {
        var list = map.get(uuid);
        if (list == null) return false;
        if (list.isEmpty()) return false;
        if (!list.contains(dialog)) return false;
        list.remove(dialog);
        if (list.isEmpty()) map.remove(uuid, list);
        return true;
    }
}
