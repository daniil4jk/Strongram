package ru.daniil4jk.strongram;

import org.telegram.telegrambots.meta.generics.TelegramClient;

public interface TelegramClientProvider {
    TelegramClient getClient();
}
