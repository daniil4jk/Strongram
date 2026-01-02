package ru.daniil4jk.strongram.core.response.dto;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Slf4j
@ToString
@EqualsAndHashCode
public class ForgetResponse<Method extends PartialBotApiMethod<?>> implements Response<Method> {
    private final Method message;
    private final SendFunction<?> send;

    @SuppressWarnings("rawtypes")
    public ForgetResponse(Method message, SendFunction function) {
        this.message = message;
        this.send = function;
    }

    @Override
    public Method getEntry() {
        return message;
    }

    @Override
    public void sendUsing(TelegramClient client) {
        try {
            var future = send.apply(client);
            if (future != null) {
                future.exceptionally(e -> {
                    exceptionally(e);
                    return null;
                });
            }
        } catch (Exception e) {
            exceptionally(e);
        }
    }

    private void exceptionally(Throwable e) {
        log.warn("cannot send message, because", e);
    }

    @Override
    public boolean isObjectRequired() {
        return false;
    }
}
