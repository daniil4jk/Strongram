package ru.daniil4jk.strongram.core.unboxer.finder.text.parser;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import ru.daniil4jk.strongram.core.unboxer.finder.Finder;
import ru.daniil4jk.strongram.core.unboxer.finder.TelegramObjectFinderException;

public abstract class TextFinder<I extends BotApiObject> implements Finder<I, String> {
    @Override
    public Class<String> getOutputClass() {
        return String.class;
    }

    protected void throwNotContainsStringException(Class<?> clazz) {
        throw new TelegramObjectFinderException("%s has`nt String payload"
                .formatted(clazz.getName()));
    }
}
