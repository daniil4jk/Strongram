package ru.daniil4jk.strongram.longpolling;

import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class MultithreadingLongPollingBotWrapper implements LongPollingUpdateConsumer {
    private final Consumer<Update> consumer;
    private final ExecutorService executor;

    public MultithreadingLongPollingBotWrapper(Consumer<Update> consumer) {
        this(consumer, Executors.newCachedThreadPool());
    }

    public MultithreadingLongPollingBotWrapper(LongPollingUpdateConsumer consumer) {
        this(consumer, Executors.newCachedThreadPool());
    }

    public MultithreadingLongPollingBotWrapper(Consumer<Update> consumer, ExecutorService executor) {
        this.consumer = consumer;
        this.executor = executor;
    }

    @SuppressWarnings("unchecked")
    public MultithreadingLongPollingBotWrapper(LongPollingUpdateConsumer lpconsumer, ExecutorService executor) {
        Consumer<Update> consumer;
        try {
            consumer = (Consumer<Update>) lpconsumer;
        } catch (Exception e) {
            consumer = update -> lpconsumer.consume(Collections.singletonList(update));
        }

        this.consumer = consumer;
        this.executor = executor;
    }

    @Override
    public void consume(List<Update> updates) {
        updates.forEach(update -> {
            executor.execute(() -> consumer.accept(update));
        });
    }
}
