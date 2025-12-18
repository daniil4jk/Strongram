package ru.daniil4jk.strongram.core.responder;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.core.bot.TelegramClientProvider;

import java.util.List;

@Slf4j
public class SimpleResponder implements Responder {
    private final TelegramClientProvider client;

    public SimpleResponder(TelegramClientProvider client) {
        this.client = client;
    }

    @Override
    public void send(@NotNull List<BotApiMethod<?>> messages) {
        for (BotApiMethod<?> message : messages) {
            try {
                client.getClient().execute(message);
            } catch (TelegramApiException e) {
                log.warn("Sending response failed", e);
            }
        }
    }
}
