package ru.daniil4jk.strongram.core.parser;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

public interface Parser<I extends BotApiObject, O> {
    Class<I> getInputClass();

    Class<O> getOutputClass();

    O parse(I t) throws TelegramObjectParseException;
}
