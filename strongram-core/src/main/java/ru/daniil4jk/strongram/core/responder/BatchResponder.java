package ru.daniil4jk.strongram.core.responder;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import ru.daniil4jk.strongram.core.util.Lazy;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class BatchResponder implements Responder {
    public static final List<Delay> DELAYS = List.of(
            new Delay(30, 50L),
            new Delay(100, 170L),
            new Delay(500, 300L)
    );

    private final Lazy<ExecutorService> batchExecutor = new Lazy<>(Executors::newSingleThreadExecutor);
    private final SimpleResponder simple;

    public BatchResponder(SimpleResponder simple) {
        this.simple = simple;
    }

    @Override
    public void send(@NotNull List<PartialBotApiMethod<?>> messages) {
        batchExecutor.initOrGet().execute(() -> sendInternal(messages));
    }

    @Override
    public void send(PartialBotApiMethod<?> message) {
        simple.send(message);
    }

    private void sendInternal(@NotNull List<PartialBotApiMethod<?>> messages) {
        Delay delay = getOptimalDelay(messages.size());

        if (delay == null) {
            simple.send(messages);
            return;
        }

        for (PartialBotApiMethod<?> message : messages) {
            try {
                simple.send(message);
                Thread.sleep(delay.delay);
            } catch (InterruptedException e) {
                log.warn("Sending response failed", e);
            }
        }
    }

    public boolean batchSendRecommended(int listSize) {
        return listSize >= DELAYS.get(0).minMessages;
    }

    private Delay getOptimalDelay(int listSize) {
        for (int i = DELAYS.size() - 1; i > 0; i--) {
            Delay delay = DELAYS.get(i);
            if (listSize > delay.minMessages) {
                return delay;
            }
        }
        return null;
    }

    public record Delay(
            int minMessages, long delay
    ) {}
}
