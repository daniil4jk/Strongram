package ru.daniil4jk.strongram.parser.payload.parser;

import org.telegram.telegrambots.meta.api.objects.payments.ShippingQuery;
import ru.daniil4jk.strongram.parser.TelegramObjectParseException;

public class ShippingQueryPayloadParser extends PayloadParser<ShippingQuery> {
    @Override
    public Class<ShippingQuery> getInputClass() {
        return ShippingQuery.class;
    }

    @Override
    public String parse(ShippingQuery t) throws TelegramObjectParseException {
        return t.getInvoicePayload();
    }
}
