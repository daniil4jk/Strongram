package ru.daniil4jk.strongram.longpolling;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class LongPollingBotApplication extends TelegramBotsLongPollingApplication {

    public BotSession registerBot(@NotNull HasBot adapter) throws TelegramApiException {
        String token = adapter.getToken();
        BotSession session = super.registerBot(token, adapter);
        if (!adapter.hasClient()) {
            adapter.setClient(new OkHttpTelegramClient(session.getOkHttpClient(), token));
        }
        return session;
    }
}
