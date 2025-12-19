package ru.daniil4jk.strongram.core.unboxer.finder;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

public interface Finder<I extends BotApiObject, O> {
    Class<I> getInputClass();

    Class<O> getOutputClass();

    O parse(I t) throws TelegramObjectFinderException;
}
