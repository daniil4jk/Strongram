package ru.daniil4jk.strongram;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;
import java.util.function.Function;

@Getter
@Slf4j
public abstract class TelegramBot implements Bot, TelegramClientProvider,
        LongPollingUpdateConsumer, Function<Update, BotApiMethod<?>> {
    private final BotCredentials credentials;
    private final TelegramClient client;

    public TelegramBot(@NotNull BotCredentials credentials) {
        this.credentials = credentials;
        this.client = new OkHttpTelegramClient(credentials.getBotToken());
    }

    public TelegramBot(@NotNull OkHttpClient httpClient, @NotNull BotCredentials credentials) {
        this.credentials = credentials;
        this.client = new OkHttpTelegramClient(httpClient, credentials.getBotToken());
    }

    public TelegramBot(@NotNull TelegramClient telegramClient, @NotNull BotCredentials credentials) {
        this.credentials = credentials;
        this.client = telegramClient;
    }

    @Override
    public BotApiMethod<?> apply(Update update) {
        try {
            return process(update);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    @Override
    public void consume(@NotNull List<Update> list) {
        for (Update update : list) {
            try {
                var method = process(update);
                if (method != null) {
                    client.execute(method);
                }
            } catch (Exception e) {
                log.error(e.getLocalizedMessage(), e);
            }
        }
    }

    @Override
    public BotCredentials getCredentials() {
        return credentials;
    }
}
