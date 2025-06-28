package ru.daniil4jk.simple_tg_lib.handler.one_time;

import ru.daniil4jk.simple_tg_lib.TelegramUUID;

import java.util.List;

public interface OneTimeHandlerService {
    void add(TelegramUUID telegramUUID, OneTimeHandler handler);
    boolean containsByUUID(TelegramUUID telegramUUID);
    OneTimeHandler getFirst(TelegramUUID telegramUUID);
    List<OneTimeHandler> getAll(TelegramUUID telegramUUID);
    void remove(TelegramUUID telegramUUID, OneTimeHandler handler);
}

