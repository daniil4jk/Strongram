package ru.daniil4jk.strongram.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.business.BusinessMessagesDeleted;
import ru.daniil4jk.strongram.TelegramUUID;
import ru.daniil4jk.strongram.parser.TelegramObjectParseException;

public class BusinessMessagesDeletedTelegramUUIDParser extends TelegramUUIDParser<BusinessMessagesDeleted> {
    @Override
    public Class<BusinessMessagesDeleted> getParsingClass() {
        return BusinessMessagesDeleted.class;
    }

    @Override
    public TelegramUUID parse(BusinessMessagesDeleted t) throws TelegramObjectParseException {
        return new TelegramUUID(t.getChat(), null);
    }
}
