package ru.daniil4jk.strongram.longpolling;

import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class MultithreadingLongPollingBotWrapper implements LongPollingUpdateConsumer {
    private final Consumer<Update> bot;
    private final ExecutorService executor;

    public MultithreadingLongPollingBotWrapper(Consumer<Update> bot) {
        this(bot, Executors.newCachedThreadPool());
    }

    public MultithreadingLongPollingBotWrapper(LongPollingUpdateConsumer bot) {
        this(bot, Executors.newCachedThreadPool());
    }

    public MultithreadingLongPollingBotWrapper(Consumer<Update> bot, ExecutorService executor) {
        this.bot = bot;
        this.executor = executor;
    }

    public MultithreadingLongPollingBotWrapper(LongPollingUpdateConsumer bot, ExecutorService executor) {
        Consumer<Update> consumer;
        try {
            consumer = (Consumer<Update>) bot;
        } catch (Exception e) {
            consumer = update -> bot.consume(Collections.singletonList(update));
        }

        this.bot = consumer;
        this.executor = executor;
    }

    @Override
    public void consume(List<Update> updates) {
        updates.forEach(update -> {
            executor.execute(() -> bot.accept(update));
        });
    }
}
