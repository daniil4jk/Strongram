package ru.daniil4jk.strongram.parser;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

public interface Parser<I extends BotApiObject, O> {
    Class<I> getParsingClass();

    O parse(I t) throws TelegramObjectParseException;
}
