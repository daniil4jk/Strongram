package ru.daniil4jk.strongram.core.parser.to.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.business.BusinessMessagesDeleted;
import ru.daniil4jk.strongram.core.dto.TelegramUUID;
import ru.daniil4jk.strongram.core.parser.TelegramObjectParseException;

public class BusinessMessagesDeletedTelegramUUIDParser extends TelegramUUIDParser<BusinessMessagesDeleted> {
    @Override
    public Class<BusinessMessagesDeleted> getInputClass() {
        return BusinessMessagesDeleted.class;
    }

    @Override
    public TelegramUUID parse(BusinessMessagesDeleted t) throws TelegramObjectParseException {
        return new TelegramUUID(t.getChat(), null);
    }
}
