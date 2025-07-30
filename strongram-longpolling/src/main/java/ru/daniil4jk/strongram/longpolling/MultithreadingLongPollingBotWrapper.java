package ru.daniil4jk.strongram.longpolling;

import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultithreadingLongPollingBotWrapper implements LongPollingUpdateConsumer {
    private final LongPollingSingleThreadUpdateConsumer bot;
    private final ExecutorService executor;

    public MultithreadingLongPollingBotWrapper(LongPollingUpdateConsumer bot) {
        this(bot, Executors.newCachedThreadPool());
    }

    public MultithreadingLongPollingBotWrapper(LongPollingSingleThreadUpdateConsumer bot) {
        this(bot, Executors.newCachedThreadPool());
    }

    public MultithreadingLongPollingBotWrapper(LongPollingUpdateConsumer bot, ExecutorService executor) {
        this.bot = update -> bot.consume(List.of(update));
        this.executor = executor;
    }

    public MultithreadingLongPollingBotWrapper(LongPollingSingleThreadUpdateConsumer bot, ExecutorService executor) {
        this.bot = bot;
        this.executor = executor;
    }

    @Override
    public void consume(List<Update> updates) {
        updates.forEach(update -> {
            executor.execute(() -> bot.consume(update));
        });
    }
}
