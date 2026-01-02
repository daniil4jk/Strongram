package ru.daniil4jk.strongram.webhook;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.client.jetty.JettyTelegramClient;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.DeleteWebhook;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegram.telegrambots.webhook.TelegramWebhookBot;
import ru.daniil4jk.strongram.core.bot.Bot;
import ru.daniil4jk.strongram.core.bot.TelegramClientProvider;
import ru.daniil4jk.strongram.webhook.response.WebhookSender;

import java.net.URI;
import java.net.URL;

@Slf4j
public class WebhookBotAdapter implements TelegramWebhookBot, TelegramClientProvider {
    @Getter
    private final String botPath;
    private final String token;
    private final SetWebhook setWebhook;
    private final Bot bot;
    private volatile TelegramClient client;

    public WebhookBotAdapter(@NotNull URL botUrl, String token, Bot bot) {
        this(new SetWebhook(botUrl.toString()), token, bot);
    }

    public WebhookBotAdapter(@NotNull SetWebhook setWebhook, String token, @NotNull Bot bot) {
        this.setWebhook = setWebhook;
        this.token = token;
        this.botPath = URI.create(setWebhook.getUrl()).getPath();
        this.bot = bot;
    }

    @Override
    public void runSetWebhook() {
        try {
            getClient().execute(setWebhook);
        } catch (TelegramApiException e) {
            log.error("Can`t setWebhook to bot on the path {}", botPath, e);
        }
    }

    @Override
    public void runDeleteWebhook() {
        try {
            getClient().execute(new DeleteWebhook());
        } catch (TelegramApiException e) {
            log.warn("Can`t deleteWebhook to bot on the path {}", botPath, e);
        }
    }

    @Override
    public BotApiMethod<?> consumeUpdate(Update update) {
        try (WebhookSender responder = new WebhookSender()) {
            bot.accept(update, responder);
            return responder.sendAll(getClient());
        } catch (Exception e) {
            log.error("Error occurred while bot processing update", e);
        }
        return null;
    }

    @Override
    public TelegramClient getClient() {
        if (client == null) {
            synchronized (this) {
                if (client == null) {
                    this.client = createClient();
                }
            }
        }
        return client;
    }

    @Override
    public void setClient(TelegramClient client) {
        this.client = client;
    }

    @Override
    public boolean hasClient() {
        return client != null;
    }

    @Contract(" -> new")
    private @NotNull TelegramClient createClient() {
        return new JettyTelegramClient(token);
    }
}
