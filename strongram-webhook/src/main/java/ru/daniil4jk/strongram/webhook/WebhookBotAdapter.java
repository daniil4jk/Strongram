package ru.daniil4jk.strongram.webhook;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.DeleteWebhook;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegram.telegrambots.webhook.TelegramWebhookBot;
import ru.daniil4jk.strongram.core.bot.Bot;
import ru.daniil4jk.strongram.core.response.client.provider.MutableTelegramClientProvider;
import ru.daniil4jk.strongram.core.response.client.provider.TelegramClientProvider;
import ru.daniil4jk.strongram.core.util.DefaultExecutor;
import ru.daniil4jk.strongram.webhook.response.WebhookSender;

import java.net.URI;
import java.net.URL;

@Slf4j
public class WebhookBotAdapter implements TelegramWebhookBot, TelegramClientProvider {
    private final TelegramClientProvider provider = new MutableTelegramClientProvider(
            this::createClient
    );
    @Getter
    private final String botPath;
    private final String token;
    private final SetWebhook setWebhook;
    private final Bot bot;
    private final WebhookSender sender;

    public WebhookBotAdapter(@NotNull URL botUrl, String token, Bot bot) {
        this(
                new SetWebhook(
                        botUrl.getProtocol() + "://" + botUrl.getHost()
                ),
                botUrl.getPath(),
                token,
                bot);
    }

    public WebhookBotAdapter(@NotNull SetWebhook setWebhook, String path, String token, @NotNull Bot bot) {
        this.setWebhook = setWebhook;
        this.token = token;
        this.botPath = URI.create(setWebhook.getUrl()).getPath();
        this.bot = bot;
        this.sender = new WebhookSender(DefaultExecutor.initOrGet(), provider);
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
        try {
            var responses = bot.apply(update);
            return sender.sendAllWebhook(responses);
        } catch (Exception e) {
            log.error("Error occurred while webhook bot processing update", e);
            return null;
        }
    }

    @Override
    public TelegramClient getClient() {
        return provider.getClient();
    }

    @Override
    public void setClient(TelegramClient client) {
        provider.setClient(client);
    }

    @Override
    public boolean hasClient() {
        return provider.hasClient();
    }

    @Contract(" -> new")
    private @NotNull TelegramClient createClient() {
        return new OkHttpTelegramClient(token);
    }
}
