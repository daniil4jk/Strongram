package ru.daniil4jk.strongram.core.parser.payload.parser;

import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import ru.daniil4jk.strongram.core.parser.TelegramObjectParseException;

public class InlineQueryPayloadParser extends PayloadParser<InlineQuery> {
    @Override
    public Class<InlineQuery> getInputClass() {
        return InlineQuery.class;
    }

    @Override
    public String parse(InlineQuery t) throws TelegramObjectParseException {
        return t.getQuery();
    }
}
