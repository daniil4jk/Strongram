package ru.daniil4jk.strongram.parser.payload.parser;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import ru.daniil4jk.strongram.parser.Parser;
import ru.daniil4jk.strongram.parser.TelegramObjectParseException;
import ru.daniil4jk.strongram.parser.payload.PayloadParserService;

public abstract class PayloadParser<I extends BotApiObject> implements Parser<I, String> {
    {
        PayloadParserService.getInstance().addParser(this);
    }

    protected void throwNotContainsStringException(Class<?> clazz) {
        throw new TelegramObjectParseException("%s has`nt String payload"
                .formatted(clazz.getName()));
    }
}
