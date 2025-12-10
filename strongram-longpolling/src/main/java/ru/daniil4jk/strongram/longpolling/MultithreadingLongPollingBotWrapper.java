package ru.daniil4jk.strongram.longpolling;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.core.bot.Bot;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultithreadingLongPollingBotWrapper extends LongPollingBotWrapper {
    private final ExecutorService executor;

    public MultithreadingLongPollingBotWrapper(String token, Bot bot) {
        this(token, bot, Executors.newCachedThreadPool());
    }

    public MultithreadingLongPollingBotWrapper(String token, Bot bot, ExecutorService executor) {
        super(token, bot);
        this.executor = executor;
    }

    @Override
    public void consume(@NotNull List<Update> updates) {
        executor.execute(
                () -> updates.forEach(this::consumeSingle)
        );
    }
}
