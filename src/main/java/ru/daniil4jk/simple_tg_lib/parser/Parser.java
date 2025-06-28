package ru.daniil4jk.simple_tg_lib.parser;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

public interface Parser<I extends BotApiObject, O> {
    Class<I> getParsingClass();

    O parse(I t);
}
