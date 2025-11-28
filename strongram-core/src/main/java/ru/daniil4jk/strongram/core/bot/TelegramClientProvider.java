package ru.daniil4jk.strongram.core.bot;

import org.telegram.telegrambots.meta.generics.TelegramClient;

public interface TelegramClientProvider {
    TelegramClient getClient();
    boolean hasClient();
    void setClient(TelegramClient client);
}
