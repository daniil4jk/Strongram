package ru.daniil4jk.strongram.parser.payload.parser;

import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import ru.daniil4jk.strongram.parser.TelegramObjectParseException;

public class PreCheckoutQueryPayloadParser extends PayloadParser<PreCheckoutQuery> {
    @Override
    public Class<PreCheckoutQuery> getInputClass() {
        return PreCheckoutQuery.class;
    }

    @Override
    public String parse(PreCheckoutQuery t) throws TelegramObjectParseException {
        return t.getInvoicePayload();
    }
}
