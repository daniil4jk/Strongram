package ru.daniil4jk.strongram.core.unboxer.finder.text.parser;

import org.telegram.telegrambots.meta.api.objects.payments.ShippingQuery;
import ru.daniil4jk.strongram.core.unboxer.finder.TelegramObjectFinderException;

public class ShippingQueryTextFinder extends TextFinder<ShippingQuery> {
    @Override
    public Class<ShippingQuery> getInputClass() {
        return ShippingQuery.class;
    }

    @Override
    public String parse(ShippingQuery t) throws TelegramObjectFinderException {
        return t.getInvoicePayload();
    }
}
