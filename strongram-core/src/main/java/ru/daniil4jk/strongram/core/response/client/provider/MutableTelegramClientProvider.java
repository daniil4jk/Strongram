package ru.daniil4jk.strongram.core.response.client.provider;

import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.function.Supplier;

public class MutableTelegramClientProvider implements TelegramClientProvider {
    private final Supplier<TelegramClient> creator;
    private volatile TelegramClient client;

    public MutableTelegramClientProvider() {
        creator = null;
    }

    public MutableTelegramClientProvider(Supplier<TelegramClient> creator) {
        this.creator = creator;
    }

    @Override
    public TelegramClient getClient() {
        if (client == null && creator != null) {
            setClient(creator.get());
        }
        return client;
    }

    @Override
    public boolean hasClient() {
        return client != null;
    }

    @Override
    public void setClient(TelegramClient newClient) {
        if (newClient == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }
        synchronized (this) {
            this.client = newClient;
        }
    }
}
