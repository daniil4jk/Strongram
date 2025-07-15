package ru.daniil4jk.strongram.parser.payload.parser;

import org.telegram.telegrambots.meta.api.objects.payments.PaidMediaPurchased;
import ru.daniil4jk.strongram.parser.TelegramObjectParseException;

public class PaidMediaPurchasedPayloadParser extends PayloadParser<PaidMediaPurchased> {
    @Override
    public Class<PaidMediaPurchased> getInputClass() {
        return PaidMediaPurchased.class;
    }

    @Override
    public String parse(PaidMediaPurchased t) throws TelegramObjectParseException {
        return t.getPaidMediaPayload();
    }
}
