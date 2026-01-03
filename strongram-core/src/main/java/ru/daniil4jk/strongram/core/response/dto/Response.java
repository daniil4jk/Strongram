package ru.daniil4jk.strongram.core.response.dto;

import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public interface Response<Method extends PartialBotApiMethod<?>> {
    Method getEntry();

    void sendUsing(TelegramClient client);

    boolean isObjectRequired();

    //todo add thenSend
}
