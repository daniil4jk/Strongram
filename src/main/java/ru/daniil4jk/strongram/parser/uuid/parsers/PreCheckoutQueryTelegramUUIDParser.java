package ru.daniil4jk.strongram.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import ru.daniil4jk.strongram.TelegramUUID;

public class PreCheckoutQueryTelegramUUIDParser extends TelegramUUIDParser<PreCheckoutQuery> {
    @Override
    public Class<PreCheckoutQuery> getParsingClass() {
        return PreCheckoutQuery.class;
    }

    @Override
    public TelegramUUID parse(PreCheckoutQuery t) {
        return new TelegramUUID(null, t.getFrom());
    }
}
