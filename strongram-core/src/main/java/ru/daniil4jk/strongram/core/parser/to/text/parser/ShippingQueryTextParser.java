package ru.daniil4jk.strongram.core.parser.to.text.parser;

import org.telegram.telegrambots.meta.api.objects.payments.ShippingQuery;
import ru.daniil4jk.strongram.core.parser.TelegramObjectParseException;

public class ShippingQueryTextParser extends TextParser<ShippingQuery> {
    @Override
    public Class<ShippingQuery> getInputClass() {
        return ShippingQuery.class;
    }

    @Override
    public String parse(ShippingQuery t) throws TelegramObjectParseException {
        return t.getInvoicePayload();
    }
}
