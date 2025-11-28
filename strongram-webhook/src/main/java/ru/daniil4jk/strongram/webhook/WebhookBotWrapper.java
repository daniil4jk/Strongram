package ru.daniil4jk.strongram.webhook;

import lombok.Getter;
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

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class WebhookBotWrapper implements TelegramWebhookBot {
    private final SetWebhook setWebhook;
    private final Bot bot;
    private final TelegramClient client;
    @Getter
    private final String botPath;

    public WebhookBotWrapper(URL botUrl, Bot bot) {
        this(new SetWebhook(botUrl.toString()), bot);
    }

    public WebhookBotWrapper(SetWebhook setWebhook, Bot bot) {
        this.setWebhook = setWebhook;
        try {
            botPath = new URL(setWebhook.getUrl()).getPath();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Cannot get path from url from setWebhook", e);
        }

        this.bot = bot;
        if (bot instanceof TelegramClientProvider provider) {
            var client = provider.getClient();
            if (client == null) {
                client = createClient(bot.getCredentials().getBotToken());
                if (provider.clientRequired()) {
                    provider.setClient(client);
                }
            }
            this.client = client;
        } else {
            this.client = createClient(bot.getCredentials().getBotToken());
        }
    }

    private TelegramClient createClient(String token) {
        return new JettyTelegramClient(token);
    }

    @Override
    public void runSetWebhook() {
        try {
            client.execute(setWebhook);
        } catch (TelegramApiException e) {
            log.error("Can`t setWebhook to bot on the path {}", getBotPath(), e);
        }
    }

    @Override
    public void runDeleteWebhook() {
        try {
            client.execute(new DeleteWebhook());
        } catch (TelegramApiException e) {
            log.warn("Can`t deleteWebhook to bot on the path {}", getBotPath(), e);
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
}
