package ru.daniil4jk.strongram.core.unboxer.finder.text.parser;

import org.telegram.telegrambots.meta.api.objects.inlinequery.ChosenInlineQuery;
import ru.daniil4jk.strongram.core.unboxer.finder.TelegramObjectFinderException;

public class ChosenInlineQueryTextFinder extends TextFinder<ChosenInlineQuery> {
    @Override
    public Class<ChosenInlineQuery> getInputClass() {
        return ChosenInlineQuery.class;
    }

    @Override
    public String parse(ChosenInlineQuery t) throws TelegramObjectFinderException {
        return t.getQuery();
    }
}
