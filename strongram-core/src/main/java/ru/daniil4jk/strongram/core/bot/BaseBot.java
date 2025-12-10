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
    @Getter
    private final String username;
    private TelegramClient client;

    public BaseBot(String username) {
        this.username = username;
    }

    public BaseBot(TelegramClient telegramClient, String username) {
        this.client = telegramClient;
        this.username = username;
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
