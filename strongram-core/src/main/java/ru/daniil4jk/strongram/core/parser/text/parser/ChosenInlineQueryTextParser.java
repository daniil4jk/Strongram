package ru.daniil4jk.strongram.core.parser.text.parser;

import org.telegram.telegrambots.meta.api.objects.inlinequery.ChosenInlineQuery;
import ru.daniil4jk.strongram.core.parser.TelegramObjectParseException;

public class ChosenInlineQueryTextParser extends TextParser<ChosenInlineQuery> {
    @Override
    public Class<ChosenInlineQuery> getInputClass() {
        return ChosenInlineQuery.class;
    }

    @Override
    public String parse(ChosenInlineQuery t) throws TelegramObjectParseException {
        return t.getQuery();
    }
}
