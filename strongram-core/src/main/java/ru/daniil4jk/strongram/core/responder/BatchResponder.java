package ru.daniil4jk.strongram.core.responder;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.daniil4jk.strongram.core.bot.TelegramClientProvider;
import ru.daniil4jk.strongram.core.util.Lazy;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class BatchResponder implements Responder {
    private final Lazy<ExecutorService> batchExecutor = new Lazy<>(Executors::newSingleThreadExecutor);
    public static final List<Delay> DELAYS = List.of(
            new Delay(30, 50L),
            new Delay(100, 170L),
            new Delay(500, 300L)
    );
    private final TelegramClientProvider client;

    public BatchResponder(TelegramClientProvider client) {
        this.client = client;
    }

    @Override
    public void send(@NotNull List<BotApiMethod<?>> messages) {
        batchExecutor.initOrGet().execute(() -> sendInternal(messages));
    }

    private void sendInternal(@NotNull List<BotApiMethod<?>> messages) {
        long delay = getOptimalDelay(messages.size());

        for (BotApiMethod<?> message : messages) {
            try {
                client.getClient().execute(message);
                Thread.sleep(delay);
            } catch (TelegramApiException | InterruptedException e) {
                log.warn("Sending response failed", e);
            }
        }
    }

    public boolean batchSendRecommended(int listSize) {
        return listSize >= DELAYS.get(0).minMessages;
    }

    private long getOptimalDelay(int listSize) {
        for (int i = DELAYS.size() - 1; i > 0; i--) {
            Delay delay = DELAYS.get(i);
            if (listSize > delay.minMessages) {
                return delay.delay;
            }
        }
        return 0L;
    }

    public record Delay(
            int minMessages, long delay
    ) {}
}
