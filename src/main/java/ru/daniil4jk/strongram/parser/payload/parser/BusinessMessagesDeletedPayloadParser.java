package ru.daniil4jk.strongram.parser.payload.parser;

import org.telegram.telegrambots.meta.api.objects.business.BusinessMessagesDeleted;
import ru.daniil4jk.strongram.parser.TelegramObjectParseException;

public class BusinessMessagesDeletedPayloadParser extends PayloadParser<BusinessMessagesDeleted> {
    @Override
    public Class<BusinessMessagesDeleted> getParsingClass() {
        return BusinessMessagesDeleted.class;
    }

    @Override
    public String parse(BusinessMessagesDeleted t) throws TelegramObjectParseException {
        throwNotContainsStringException(t.getClass());
        return null;
    }
}
