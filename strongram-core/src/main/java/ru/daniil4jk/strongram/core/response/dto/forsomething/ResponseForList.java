package ru.daniil4jk.strongram.core.response.dto.forsomething;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.core.response.dto.SendFunction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@ToString
@EqualsAndHashCode
public class ResponseForList<Method extends PartialBotApiMethod<ArrayList<Object>>, Object extends Serializable>
        implements ResponseForSomething<Method, ArrayList<Object>> {

    private final Method message;
    private final SendFunction<List<Object>> send;
    private final CompletableFuture<ArrayList<Object>> future = new CompletableFuture<>();

    public ResponseForList(Method message, SendFunction<List<Object>> send) {
        this.message = message;
        this.send = send;
    }

    @Override
    public Method getEntry() {
        return message;
    }

    @Override
    public void sendUsing(TelegramClient client) {
        try {
            send.apply(client)
                    .thenApply(list -> {
                        if (list instanceof ArrayList<Object> array) {
                            return array;
                        } else {
                            return new ArrayList<>(list);
                        }
                    })
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
    public CompletableFuture<ArrayList<Object>> getObject() {
        return future;
    }

    @Override
    public boolean isObjectRequired() {
        return true;
    }
}
