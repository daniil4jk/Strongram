package ru.daniil4jk.strongram.core.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.payments.ShippingQuery;
import ru.daniil4jk.strongram.core.chain.context.TelegramUUID;
import ru.daniil4jk.strongram.core.parser.TelegramObjectParseException;

public class ShippingQueryTelegramUUIDParser extends TelegramUUIDParser<ShippingQuery> {
    @Override
    public Class<ShippingQuery> getInputClass() {
        return ShippingQuery.class;
    }

    @Override
    public TelegramUUID parse(ShippingQuery t) throws TelegramObjectParseException {
        return new TelegramUUID(null, t.getFrom());
    }
}
