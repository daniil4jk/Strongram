package ru.daniil4jk.strongram.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.inlinequery.ChosenInlineQuery;
import ru.daniil4jk.strongram.TelegramUUID;

public class ChosenInlineQueryParserTelegram extends TelegramUUIDParser<ChosenInlineQuery> {
    @Override
    public Class<ChosenInlineQuery> getParsingClass() {
        return ChosenInlineQuery.class;
    }

    @Override
    public TelegramUUID parse(ChosenInlineQuery t) {
        return new TelegramUUID(null, t.getFrom());
    }
}
