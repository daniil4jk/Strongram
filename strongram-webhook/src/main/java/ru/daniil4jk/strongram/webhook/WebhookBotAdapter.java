package ru.daniil4jk.strongram.webhook;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.telegram.telegrambots.client.jetty.JettyTelegramClient;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.DeleteWebhook;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegram.telegrambots.webhook.TelegramWebhookBot;
import ru.daniil4jk.strongram.core.bot.Bot;
import ru.daniil4jk.strongram.core.responder.Responder;
import ru.daniil4jk.strongram.core.responder.SelectiveResponder;

import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Objects;

@Slf4j
public class WebhookBotAdapter implements TelegramWebhookBot {
    @Getter
    private final String botPath;
    private final SetWebhook setWebhook;
    private final Bot bot;
    private final Responder responder;

    public WebhookBotAdapter(@NotNull URL botUrl, String token, Bot bot) {
        this(new SetWebhook(botUrl.toString()), token, bot);
    }

    public WebhookBotAdapter(@NotNull SetWebhook setWebhook, String token, @NotNull Bot bot) {
        this.setWebhook = setWebhook;
        this.botPath = URI.create(setWebhook.getUrl()).getPath();

        this.bot = bot;
        this.responder = new SelectiveResponder(bot);

        if (!bot.hasClient()) {
            bot.setClient(createClient(token));
        }
    }

    @Contract("_ -> new")
    private @NotNull TelegramClient createClient(String token) {
        return new JettyTelegramClient(token);
    }

    @Override
    public void runSetWebhook() {
        try {
            bot.getClient().execute(setWebhook);
        } catch (TelegramApiException e) {
            log.error("Can`t setWebhook to bot on the path {}", botPath, e);
        }
    }

    @Override
    public void runDeleteWebhook() {
        try {
            bot.getClient().execute(new DeleteWebhook());
        } catch (TelegramApiException e) {
            log.warn("Can`t deleteWebhook to bot on the path {}", botPath, e);
        }
    }

    @Override
    public BotApiMethod<?> consumeUpdate(Update update) {
        List<BotApiMethod<?>> responses = bot.apply(update);
        return sendResponses(responses);
    }

    private @Nullable BotApiMethod<?> sendResponses(List<BotApiMethod<?>> responses) {
        Objects.requireNonNull(
                responses,
                "responses cannot be null, please return Collections.emptyList()"
        );

        return switch (responses.size()) {
            case 0 -> null;
            case 1 -> responses.get(0);
            default -> {
                BotApiMethod<?> lastMessage = responses.remove(responses.size() - 1);
                responder.send(responses);
                yield lastMessage;
            }
        };
    }
}
