package ru.daniil4jk.strongram.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.payments.ShippingQuery;
import ru.daniil4jk.strongram.TelegramUUID;

public class ShippingQueryTelegramUUIDParser extends TelegramUUIDParser<ShippingQuery> {
    @Override
    public Class<ShippingQuery> getParsingClass() {
        return ShippingQuery.class;
    }

    @Override
    public TelegramUUID parse(ShippingQuery t) {
        return new TelegramUUID(null, t.getFrom());
    }
}
