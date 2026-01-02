package ru.daniil4jk.strongram.core.response.dto.forsomething;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.core.response.dto.SendFunction;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class ResponseForObject<Method extends PartialBotApiMethod<Object>, Object extends Serializable>
        implements ResponseForSomething<Method, Object> {
    private final Method message;
    private final SendFunction<Object> send;
    private final CompletableFuture<Object> future = new CompletableFuture<>();

    @Override
    public Method getEntry() {
        return message;
    }

    @Override
    public void sendUsing(TelegramClient client) {
        try {
            send.apply(client)
                    .thenAccept(future::complete)
                    .exceptionally(e -> {
                        future.completeExceptionally(e);
                        return null;
                    });
        } catch (TelegramApiException e) {
            future.completeExceptionally(e);
        }
    }

    @Override
    public boolean isObjectRequired() {
        return true;
    }

    @Override
    public CompletableFuture<Object> getObject() {
        return future;
    }
}
