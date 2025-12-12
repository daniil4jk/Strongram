package ru.daniil4jk.strongram.core.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.payments.PaidMediaPurchased;
import ru.daniil4jk.strongram.core.context.request.TelegramUUID;
import ru.daniil4jk.strongram.core.parser.TelegramObjectParseException;

public class PaidMediaPurchasedTelegramUUIDParser extends TelegramUUIDParser<PaidMediaPurchased> {
    @Override
    public Class<PaidMediaPurchased> getInputClass() {
        return PaidMediaPurchased.class;
    }

    @Override
    public TelegramUUID parse(PaidMediaPurchased t) throws TelegramObjectParseException {
        return new TelegramUUID(null, t.getUser());
    }
}
