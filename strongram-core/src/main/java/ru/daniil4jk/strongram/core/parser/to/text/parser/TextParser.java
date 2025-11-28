package ru.daniil4jk.strongram.core.parser.to.text.parser;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import ru.daniil4jk.strongram.core.parser.Parser;
import ru.daniil4jk.strongram.core.parser.TelegramObjectParseException;

public abstract class TextParser<I extends BotApiObject> implements Parser<I, String> {
    @Override
    public Class<String> getOutputClass() {
        return String.class;
    }

    protected void throwNotContainsStringException(Class<?> clazz) {
        throw new TelegramObjectParseException("%s has`nt String payload"
                .formatted(clazz.getName()));
    }
}
