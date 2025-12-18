package ru.daniil4jk.strongram.longpolling;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.core.bot.Bot;
import ru.daniil4jk.strongram.core.bot.TelegramClientProvider;
import ru.daniil4jk.strongram.core.responder.Responder;
import ru.daniil4jk.strongram.core.responder.SelectiveResponder;

import java.util.List;
import java.util.Objects;

@Slf4j
public class LongPollingBotAdapter implements HasBot {
    @Getter
    private final String token;
    private final Bot bot;
    private final Responder responder;

    public LongPollingBotAdapter(String token, Bot bot) {
        this.token = token;
        this.bot = bot;
        this.responder = new SelectiveResponder(bot);
    }

    @Override
    public void consume(@NotNull List<Update> updates) {
        updates.forEach(this::consumeSingle);
    }

    public void consumeSingle(Update update) {
        try {
            List<BotApiMethod<?>> responses = bot.apply(update);
            sendResponses(responses);
        } catch (Exception e) {
            log.error("Error occurred while bot processing update", e);
        }
    }

    private void sendResponses(List<BotApiMethod<?>> responses) {
        responder.send(
            Objects.requireNonNull(
                    responses,
                "responses cannot be null, please return Collections.emptyList()"
            )
        );
    }

    @Override
    public TelegramClient getClient() {
        return bot.getClient();
    }

    @Override
    public boolean hasClient() {
        return bot.hasClient();
    }

    @Override
    public void setClient(TelegramClient client) {
        bot.setClient(client);
    }
}
