package ru.daniil4jk.strongram.core.parser.to.text.parser;

import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import ru.daniil4jk.strongram.core.parser.TelegramObjectParseException;

public class PreCheckoutQueryTextParser extends TextParser<PreCheckoutQuery> {
    @Override
    public Class<PreCheckoutQuery> getInputClass() {
        return PreCheckoutQuery.class;
    }

    @Override
    public String parse(PreCheckoutQuery t) throws TelegramObjectParseException {
        return t.getInvoicePayload();
    }
}
