package ru.daniil4jk.strongram.parser.payload.parser;

import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import ru.daniil4jk.strongram.parser.TelegramObjectParseException;

public class InlineQueryPayloadParser extends PayloadParser<InlineQuery> {
    @Override
    public Class<InlineQuery> getParsingClass() {
        return InlineQuery.class;
    }

    @Override
    public String parse(InlineQuery t) throws TelegramObjectParseException {
        return t.getQuery();
    }
}
