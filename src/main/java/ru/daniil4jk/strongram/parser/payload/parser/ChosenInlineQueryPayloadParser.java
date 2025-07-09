package ru.daniil4jk.strongram.parser.payload.parser;

import org.telegram.telegrambots.meta.api.objects.inlinequery.ChosenInlineQuery;
import ru.daniil4jk.strongram.parser.TelegramObjectParseException;

public class ChosenInlineQueryPayloadParser extends PayloadParser<ChosenInlineQuery> {
    @Override
    public Class<ChosenInlineQuery> getParsingClass() {
        return ChosenInlineQuery.class;
    }

    @Override
    public String parse(ChosenInlineQuery t) throws TelegramObjectParseException {
        return t.getQuery();
    }
}
