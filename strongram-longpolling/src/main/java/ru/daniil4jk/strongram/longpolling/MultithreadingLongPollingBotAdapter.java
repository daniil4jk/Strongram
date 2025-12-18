package ru.daniil4jk.strongram.longpolling;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultithreadingLongPollingBotAdapter implements HasBot {
    private final ExecutorService executor;
    private final HasBot adapter;

    public MultithreadingLongPollingBotAdapter(HasBot adapter) {
        this(Executors.newCachedThreadPool(), adapter);
    }

    public MultithreadingLongPollingBotAdapter(ExecutorService executor, HasBot adapter) {
        this.executor = executor;
        this.adapter = adapter;
    }

    @Override
    public void consume(List<Update> updates) {
        executor.execute(
                () -> adapter.consume(updates)
        );
    }

    @Override
    public TelegramClient getClient() {
        return adapter.getClient();
    }

    @Override
    public boolean hasClient() {
        return adapter.hasClient();
    }

    @Override
    public void setClient(TelegramClient client) {
        adapter.setClient(client);
    }

    @Override
    public String getToken() {
        return adapter.getToken();
    }
}
