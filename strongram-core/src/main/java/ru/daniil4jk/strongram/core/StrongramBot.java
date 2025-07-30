package ru.daniil4jk.strongram.core;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Getter
@Slf4j
@ToString
@EqualsAndHashCode
public abstract class StrongramBot implements Bot, TelegramClientProvider {
    private final BotCredentials credentials;
    private TelegramClient client;

    public StrongramBot(BotCredentials credentials) {
        this.credentials = credentials;
    }

    public StrongramBot(TelegramClient telegramClient, BotCredentials credentials) {
        this.credentials = credentials;
        this.client = telegramClient;
    }

    @Override
    public boolean canSetClient() {
        return client == null;
    }

    @Override
    public void setClientOnce(TelegramClient client) {
        if (this.client != null) {
            throw new IllegalCallerException("Client already sat");
        }
        synchronized (this) {
            this.client = client;
        }
    }

    @Override
    public BotCredentials getCredentials() {
        return credentials;
    }
}
