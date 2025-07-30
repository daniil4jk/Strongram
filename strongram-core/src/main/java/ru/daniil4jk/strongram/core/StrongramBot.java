package ru.daniil4jk.strongram.core;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Getter
@Slf4j
public abstract class StrongramBot implements Bot, TelegramClientProvider {
    private final BotCredentials credentials;
    private final TelegramClient client;

    public StrongramBot(@NotNull BotCredentials credentials) {
        this.credentials = credentials;
        this.client = new OkHttpTelegramClient(credentials.getBotToken());
    }

    public StrongramBot(@NotNull OkHttpClient httpClient, @NotNull BotCredentials credentials) {
        this.credentials = credentials;
        this.client = new OkHttpTelegramClient(httpClient, credentials.getBotToken());
    }

    public StrongramBot(@NotNull TelegramClient telegramClient, @NotNull BotCredentials credentials) {
        this.credentials = credentials;
        this.client = telegramClient;
    }

    @Override
    public BotCredentials getCredentials() {
        return credentials;
    }
}
