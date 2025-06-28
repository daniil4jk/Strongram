package ru.daniil4jk.simple_tg_lib.handler.one_time;

import ru.daniil4jk.simple_tg_lib.TelegramUUID;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class OneTimeHandlerServiceImpl implements OneTimeHandlerService {
    private final Map<TelegramUUID, List<OneTimeHandler>> handlers = new ConcurrentHashMap<>();

    @Override
    public void add(TelegramUUID telegramUUID, OneTimeHandler handler) {
        handlers.computeIfAbsent(telegramUUID, t -> new CopyOnWriteArrayList<>());
    }

    @Override
    public boolean containsByUUID(TelegramUUID telegramUUID) {
        return handlers.containsKey(telegramUUID) && !handlers.get(telegramUUID).isEmpty();
    }

    @Override
    public OneTimeHandler getFirst(TelegramUUID telegramUUID) {
        return handlers.get(telegramUUID).get(0);
    }

    @Override
    public List<OneTimeHandler> getAll(TelegramUUID telegramUUID) {
        return handlers.getOrDefault(telegramUUID, List.of());
    }

    @Override
    public void remove(TelegramUUID telegramUUID, OneTimeHandler handler) {
        handlers.get(telegramUUID).remove(handler);
        if (handlers.get(telegramUUID).isEmpty()) handlers.remove(telegramUUID);
    }
}
