package ru.daniil4jk.strongram.longpolling.adapter;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.core.bot.Bot;
import ru.daniil4jk.strongram.core.util.DefaultExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

public class MultithreadingAdapter extends LongPollingBotAdapter {
    private final ExecutorService executor;

    public MultithreadingAdapter(String token, Bot bot) {
        this(
                token,
                bot,
                DefaultExecutor.initOrGet()
        );
    }

    public MultithreadingAdapter(String token, Bot bot, ScheduledExecutorService executor) {
        this(
                token,
                bot,
                executor,
                executor
        );
    }

    public MultithreadingAdapter(String token,
                                 Bot bot,
                                 ExecutorService mainExecutor,
                                 ScheduledExecutorService sendExecutor) {
        super(token, sendExecutor, bot);
        this.executor = mainExecutor;
    }

    @Override
    public void consumeSingle(Update update) {
        executor.execute(() -> super.consumeSingle(update));
    }
}
