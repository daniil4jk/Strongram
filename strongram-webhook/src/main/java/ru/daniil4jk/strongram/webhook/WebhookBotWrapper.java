package ru.daniil4jk.strongram.webhook;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.client.jetty.JettyTelegramClient;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.DeleteWebhook;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegram.telegrambots.webhook.TelegramWebhookBot;
import ru.daniil4jk.strongram.core.Bot;
import ru.daniil4jk.strongram.core.TelegramClientProvider;

import java.net.URL;

@Slf4j
public class WebhookBotWrapper implements TelegramWebhookBot {
    private final URL botUrl;
    private final Bot bot;
    private volatile TelegramClient client;

    public WebhookBotWrapper(URL botUrl, Bot bot) {
        this.botUrl = botUrl;
        this.bot = bot;
        if (bot instanceof TelegramClientProvider provider) {
            var client = provider.getClient();
            if (client == null) {
                this.client = client = new JettyTelegramClient(bot.getCredentials().getBotToken());
                if (provider.canSetClient()) {
                    provider.setClientOnce(client);
                }
            }
        }
    }

    @Override
    public void runDeleteWebhook() {
        try {
            client.execute(DeleteWebhook.builder()
                    .dropPendingUpdates(false)
                    .build());
        } catch (TelegramApiException e) {
            log.warn("Can`t deleteWebhook to bot on the path {}", getBotPath(), e);
        }
    }

    @Override
    public void runSetWebhook() {
        try {
            client.execute(SetWebhook.builder()
                    .url(botUrl.toString())
                    .build());
        } catch (TelegramApiException e) {
            log.error("Can`t setWebhook to bot on the path {}", getBotPath(), e);
        }
    }

    @Override
    public BotApiMethod<?> consumeUpdate(Update update) {
        try {
            return bot.process(update);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    @Override
    public String getBotPath() {
        return botUrl.getPath();
    }
}
