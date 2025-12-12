package ru.daniil4jk.strongram.core.unboxer.parser.text.parser;

import org.telegram.telegrambots.meta.api.objects.payments.PaidMediaPurchased;
import ru.daniil4jk.strongram.core.unboxer.parser.TelegramObjectParseException;

public class PaidMediaPurchasedTextParser extends TextParser<PaidMediaPurchased> {
    @Override
    public Class<PaidMediaPurchased> getInputClass() {
        return PaidMediaPurchased.class;
    }

    @Override
    public String parse(PaidMediaPurchased t) throws TelegramObjectParseException {
        return t.getPaidMediaPayload();
    }
}
