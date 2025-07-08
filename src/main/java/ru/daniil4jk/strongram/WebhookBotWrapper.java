package ru.daniil4jk.strongram;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.DeleteWebhook;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegram.telegrambots.webhook.TelegramWebhookBot;

import java.net.URL;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
public class WebhookBotWrapper implements TelegramWebhookBot {
    private final URL pathToYourServer;
    private final Function<Update, BotApiMethod<?>> bot;
    private final TelegramClient client;

    public WebhookBotWrapper(URL pathToYourServer, TelegramBot bot) {
        this.pathToYourServer = pathToYourServer;
        this.bot = bot;
        client = bot.getClient();
    }

    @Override
    public void runDeleteWebhook() {
        try {
            client.execute(DeleteWebhook.builder()
                    .dropPendingUpdates(true)
                    .build());
        } catch (TelegramApiException e) {
            log.warn("Can`t deleteWebhook to bot on the path {}", getBotPath(), e);
        }
    }

    @Override
    public void runSetWebhook() {

        try {
            client.execute(SetWebhook.builder()
                    .url(pathToYourServer.toString())
                    .build());
        } catch (TelegramApiException e) {
            log.error("Can`t setWebhook to bot on the path {}", getBotPath(), e);
        }
    }

    @Override
    public BotApiMethod<?> consumeUpdate(Update update) {
        return bot.apply(update);
    }

    @Override
    public String getBotPath() {
        return pathToYourServer.getPath();
    }
}
