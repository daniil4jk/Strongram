package ru.daniil4jk.strongram.core.bot;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Getter
@Slf4j
@ToString
@EqualsAndHashCode
public abstract class BaseBot implements Bot {
    private final BotCredentials credentials;
    private TelegramClient client;

    public BaseBot(BotCredentials credentials) {
        this.credentials = credentials;
    }

    public BaseBot(TelegramClient telegramClient, BotCredentials credentials) {
        this.credentials = credentials;
        this.client = telegramClient;
    }

    @Override
    public BotCredentials getCredentials() {
        return credentials;
    }

    @Override
    public boolean hasClient() {
        return client != null;
    }

    @Override
    public void setClient(TelegramClient client) {
        if (this.client != null) {
            throw new IllegalCallerException("Client already sat");
        }
        synchronized (this) {
            this.client = client;
        }
    }
}
