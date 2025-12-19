package ru.daniil4jk.strongram.core.unboxer.finder.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.payments.PaidMediaPurchased;
import ru.daniil4jk.strongram.core.context.request.TelegramUUID;
import ru.daniil4jk.strongram.core.unboxer.finder.TelegramObjectFinderException;

public class PaidMediaPurchasedTelegramUUIDFinder extends TelegramUUIDFinder<PaidMediaPurchased> {
    @Override
    public Class<PaidMediaPurchased> getInputClass() {
        return PaidMediaPurchased.class;
    }

    @Override
    public TelegramUUID parse(PaidMediaPurchased t) throws TelegramObjectFinderException {
        return new TelegramUUID(null, t.getUser());
    }
}
