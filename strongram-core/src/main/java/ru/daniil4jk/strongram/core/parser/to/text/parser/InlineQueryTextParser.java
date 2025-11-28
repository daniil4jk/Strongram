package ru.daniil4jk.strongram.core.parser.to.text.parser;

import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import ru.daniil4jk.strongram.core.parser.TelegramObjectParseException;

public class InlineQueryTextParser extends TextParser<InlineQuery> {
    @Override
    public Class<InlineQuery> getInputClass() {
        return InlineQuery.class;
    }

    @Override
    public String parse(InlineQuery t) throws TelegramObjectParseException {
        return t.getQuery();
    }
}
