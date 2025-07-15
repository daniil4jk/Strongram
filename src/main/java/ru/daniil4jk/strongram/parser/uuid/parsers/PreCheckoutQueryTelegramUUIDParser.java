package ru.daniil4jk.strongram.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import ru.daniil4jk.strongram.TelegramUUID;
import ru.daniil4jk.strongram.parser.TelegramObjectParseException;

public class PreCheckoutQueryTelegramUUIDParser extends TelegramUUIDParser<PreCheckoutQuery> {
    @Override
    public Class<PreCheckoutQuery> getInputClass() {
        return PreCheckoutQuery.class;
    }

    @Override
    public TelegramUUID parse(PreCheckoutQuery t) throws TelegramObjectParseException {
        return new TelegramUUID(null, t.getFrom());
    }
}
