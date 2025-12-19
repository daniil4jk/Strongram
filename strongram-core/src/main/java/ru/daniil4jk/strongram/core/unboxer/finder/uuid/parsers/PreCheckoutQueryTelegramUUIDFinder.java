package ru.daniil4jk.strongram.core.unboxer.finder.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import ru.daniil4jk.strongram.core.context.request.TelegramUUID;
import ru.daniil4jk.strongram.core.unboxer.finder.TelegramObjectFinderException;

public class PreCheckoutQueryTelegramUUIDFinder extends TelegramUUIDFinder<PreCheckoutQuery> {
    @Override
    public Class<PreCheckoutQuery> getInputClass() {
        return PreCheckoutQuery.class;
    }

    @Override
    public TelegramUUID parse(PreCheckoutQuery t) throws TelegramObjectFinderException {
        return new TelegramUUID(null, t.getFrom());
    }
}
