package ru.daniil4jk.strongram.handler.one_time;

import ru.daniil4jk.strongram.TelegramUUID;

import java.util.List;

public interface OneTimeHandlerRegistry {
    void add(TelegramUUID telegramUUID, OneTimeHandler handler);
    boolean containsByUUID(TelegramUUID telegramUUID);
    OneTimeHandler getFirstByUUID(TelegramUUID telegramUUID);
    OneTimeHandler getLastByUUID(TelegramUUID telegramUUID);
    List<OneTimeHandler> getAllByUUID(TelegramUUID telegramUUID);
    void remove(TelegramUUID telegramUUID, OneTimeHandler handler);
}

