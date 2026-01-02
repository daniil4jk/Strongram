package ru.daniil4jk.strongram.core.response.entity;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.concurrent.CompletableFuture;

public interface SendFunction<Result> {
    CompletableFuture<Result> apply(TelegramClient client) throws TelegramApiException;
}