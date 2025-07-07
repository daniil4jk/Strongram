package ru.daniil4jk.strongram.handler.one_time;

import ru.daniil4jk.strongram.TelegramUUID;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class OneTimeHandlerRegistryImpl implements OneTimeHandlerRegistry {
    Map<TelegramUUID, List<OneTimeHandler>> handlersMap = new ConcurrentHashMap<>();

    @Override
    public void add(TelegramUUID telegramUUID, OneTimeHandler handler) {
        handlersMap.computeIfAbsent(telegramUUID, k -> new CopyOnWriteArrayList<>())
                .add(handler);
    }

    @Override
    public boolean containsByUUID(TelegramUUID telegramUUID) {
        return handlersMap.containsKey(telegramUUID);
    }

    @Override
    public OneTimeHandler getFirstByUUID(TelegramUUID telegramUUID) {
        var list = handlersMap.get(telegramUUID);
        checkList(list);
        return list.get(0);
    }

    @Override
    public OneTimeHandler getLastByUUID(TelegramUUID telegramUUID) {
        var list = handlersMap.get(telegramUUID);
        checkList(list);
        return list.get(list.size() - 1);
    }

    @Override
    public List<OneTimeHandler> getAllByUUID(TelegramUUID telegramUUID) {
        var list = handlersMap.get(telegramUUID);
        checkList(list);
        return list;
    }

    private void checkList(List<?> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalStateException("Has no handlers");
        }
    }

    @Override
    public void remove(TelegramUUID telegramUUID, OneTimeHandler handler) {
        handlersMap.computeIfPresent(telegramUUID, (key, list) -> {
            list.remove(handler);
            return list.isEmpty() ? null : list;
        });
    }
}
