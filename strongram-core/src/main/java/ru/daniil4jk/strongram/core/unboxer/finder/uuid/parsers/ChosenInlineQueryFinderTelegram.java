package ru.daniil4jk.strongram.core.unboxer.finder.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.inlinequery.ChosenInlineQuery;
import ru.daniil4jk.strongram.core.context.request.TelegramUUID;
import ru.daniil4jk.strongram.core.unboxer.finder.TelegramObjectFinderException;

public class ChosenInlineQueryFinderTelegram extends TelegramUUIDFinder<ChosenInlineQuery> {
    @Override
    public Class<ChosenInlineQuery> getInputClass() {
        return ChosenInlineQuery.class;
    }

    @Override
    public TelegramUUID parse(ChosenInlineQuery t) throws TelegramObjectFinderException {
        return new TelegramUUID(null, t.getFrom());
    }
}
