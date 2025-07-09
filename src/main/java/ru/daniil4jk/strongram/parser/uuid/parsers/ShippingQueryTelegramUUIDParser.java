package ru.daniil4jk.strongram.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.payments.ShippingQuery;
import ru.daniil4jk.strongram.TelegramUUID;
import ru.daniil4jk.strongram.parser.TelegramObjectParseException;

public class ShippingQueryTelegramUUIDParser extends TelegramUUIDParser<ShippingQuery> {
    @Override
    public Class<ShippingQuery> getParsingClass() {
        return ShippingQuery.class;
    }

    @Override
    public TelegramUUID parse(ShippingQuery t) throws TelegramObjectParseException {
        return new TelegramUUID(null, t.getFrom());
    }
}
