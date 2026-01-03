package ru.daniil4jk.strongram.core.response.sender;

import org.jetbrains.annotations.NotNull;
import ru.daniil4jk.strongram.core.response.client.provider.TelegramClientProvider;
import ru.daniil4jk.strongram.core.response.dto.Response;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Sender {
    private final ScheduledExecutorService executor;
    private final TelegramClientProvider client;

    public Sender(ScheduledExecutorService executor, TelegramClientProvider client) {
        this.executor = executor;
        this.client = client;
    }

    public void sendAllUsingClient(@NotNull List<Response<?>> messages) {
        final long delayBetweenMessages = Delays.getOptimalDelayInMills(messages);
        long currentDelay = 0;

        for (Response<?> response : messages) {
            executor.schedule(
                    () -> response.sendUsing(client.getClient()),
                    currentDelay,
                    TimeUnit.MILLISECONDS
            );
            currentDelay += delayBetweenMessages;
        }
    }
}
