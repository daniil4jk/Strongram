package ru.daniil4jk.strongram.core;

import org.telegram.telegrambots.meta.generics.TelegramClient;

public interface TelegramClientProvider {
    TelegramClient getClient();
    boolean canSetClient();
    void setClientOnce(TelegramClient client);
}
