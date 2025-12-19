package ru.daniil4jk.strongram.core.responder;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.daniil4jk.strongram.core.bot.TelegramClientProvider;

import java.util.List;

@Slf4j
public class SimpleResponder implements Responder {
    private final TelegramClientProvider client;

    public SimpleResponder(TelegramClientProvider client) {
        this.client = client;
    }

    @Override
    public void send(@NotNull List<PartialBotApiMethod<?>> messages) {
        for (PartialBotApiMethod<?> message : messages) {
            try {
                sendSpecify(message);
            } catch (TelegramApiException e) {
                log.warn("Sending response failed", e);
            }
        }
    }

    public void send(PartialBotApiMethod<?> message) {
        try {
            sendSpecify(message);
        } catch (TelegramApiException e) {
            log.warn("Sending response failed", e);
        }
    }

    private void sendSpecify(PartialBotApiMethod<?> method) throws TelegramApiException {
        var c = client.getClient();
        if (method instanceof BotApiMethod<?> m) c.execute(m);
        if (method instanceof SendPhoto m) c.execute(m);
        if (method instanceof SendVideo m) c.execute(m);
        if (method instanceof SendAudio m) c.execute(m);
        if (method instanceof SendVoice m) c.execute(m);
        if (method instanceof SendDocument m) c.execute(m);
        if (method instanceof SendMediaGroup m) c.execute(m);
        if (method instanceof SendSticker m) c.execute(m);
        if (method instanceof SendPaidMedia m) c.execute(m);
        if (method instanceof BotApiMethod<?> m) c.execute(m);
        throw new IllegalArgumentException("can`t send " + method.getClass().getSimpleName());
    }
}
