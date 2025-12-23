package ru.daniil4jk.strongram.core.responder;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import ru.daniil4jk.strongram.core.util.Lazy;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class BatchResponder implements Responder {
    public static final Delay NULL_DELAY = new Delay(0, 0L);
    public static final List<Delay> DELAYS = List.of(
            NULL_DELAY,
            new Delay(30, 50L),
            new Delay(100, 170L),
            new Delay(500, 300L)
    );

    private final Lazy<ScheduledExecutorService> scheduler = new Lazy<>(Executors::newSingleThreadScheduledExecutor);
    private final SimpleResponder simple;

    public BatchResponder(SimpleResponder simple) {
        this.simple = simple;
    }

    @Override
    public void send(@NotNull List<PartialBotApiMethod<?>> messages) {
        if (simpleSendRecommended(messages.size())) {
            simple.send(messages);
            return;
        }

        Delay delay = getOptimalDelay(messages.size());
        ScheduledExecutorService executor = scheduler.initOrGet();

        for (int i = 0; i < messages.size(); i++) {
            PartialBotApiMethod<?> message = messages.get(i);
            executor.schedule(
                () -> send(message),
                i * delay.delayMs,
                TimeUnit.MILLISECONDS
            );
        }
    }

    @Override
    public void send(PartialBotApiMethod<?> message) {
        simple.send(message);
    }

    public boolean simpleSendRecommended(int listSize) {
        return NULL_DELAY.equals(getOptimalDelay(listSize));
    }

    private Delay getOptimalDelay(int listSize) {
        for (Delay delay : DELAYS) {
            if (listSize >= delay.minMessages) {
                return delay;
            }
        }
        return NULL_DELAY;
    }

    public record Delay(
            int minMessages, long delayMs
    ) implements Comparable<Delay> {
        @Override
        public int compareTo(@NotNull Delay o) {
            return Integer.compare(minMessages, o.minMessages);
        }
    }
}