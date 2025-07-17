package ru.daniil4jk.strongram;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class MultithreadingLongPollingBotWrapper implements LongPollingUpdateConsumer {
    private final LongPollingSingleThreadUpdateConsumer bot;
    private final ExecutorService executor;

    public MultithreadingLongPollingBotWrapper(LongPollingUpdateConsumer bot) {
        this(bot, Executors.newCachedThreadPool());
    }

    public MultithreadingLongPollingBotWrapper(LongPollingUpdateConsumer bot, ExecutorService executor) {
        if (bot instanceof LongPollingSingleThreadUpdateConsumer sBot) {
            this.bot = sBot;
        } else {
            this.bot = update -> bot.consume(List.of(update));
        }
        this.executor = executor;
    }

    @Override
    public void consume(List<Update> updates) {
        updates.forEach(update -> {
            executor.execute(() -> bot.consume(update));
        });
    }
}
