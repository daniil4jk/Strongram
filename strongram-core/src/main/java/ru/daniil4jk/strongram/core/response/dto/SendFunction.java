package ru.daniil4jk.strongram.core.response.dto;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public interface SendFunction<Result> {
    Result apply(TelegramClient client) throws TelegramApiException;
}