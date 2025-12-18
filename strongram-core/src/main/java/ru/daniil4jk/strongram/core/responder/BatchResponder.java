package ru.daniil4jk.strongram.core.responder;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.core.bot.TelegramClientProvider;

import java.util.List;

@Slf4j
public class BatchResponder implements Responder {
    private static final long DELAY = 50L;
    private final TelegramClientProvider client;

    public BatchResponder(TelegramClientProvider client) {
        this.client = client;
    }

    @Override
    public void send(@NotNull List<BotApiMethod<?>> messages) {
        for (BotApiMethod<?> message : messages) {
            try {
                client.getClient().execute(message);
                Thread.sleep(DELAY);
            } catch (TelegramApiException | InterruptedException e) {
                log.warn("Sending response failed", e);
            }
        }
    }
}
