package ru.daniil4jk.strongram.core.parser.to.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.inlinequery.ChosenInlineQuery;
import ru.daniil4jk.strongram.core.dto.TelegramUUID;
import ru.daniil4jk.strongram.core.parser.TelegramObjectParseException;

public class ChosenInlineQueryParserTelegram extends TelegramUUIDParser<ChosenInlineQuery> {
    @Override
    public Class<ChosenInlineQuery> getInputClass() {
        return ChosenInlineQuery.class;
    }

    @Override
    public TelegramUUID parse(ChosenInlineQuery t) throws TelegramObjectParseException {
        return new TelegramUUID(null, t.getFrom());
    }
}
