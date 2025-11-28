package ru.daniil4jk.strongram.longpolling;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class LongPollingBotApplication extends TelegramBotsLongPollingApplication {

    public BotSession registerBot(LongPollingBotWrapper bot) throws TelegramApiException {
        String token = bot.getCredentials().getToken();
        BotSession session = super.registerBot(token, bot);
        if (!bot.hasClient()) {
            bot.setClient(new OkHttpTelegramClient(session.getOkHttpClient(), token));
        }
        return session;
    }
}
