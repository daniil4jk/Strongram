package ru.daniil4jk.strongram.core.unboxer.parser.text.parser;

import org.telegram.telegrambots.meta.api.objects.business.BusinessMessagesDeleted;
import ru.daniil4jk.strongram.core.unboxer.parser.TelegramObjectParseException;

public class BusinessMessagesDeletedTextParser extends TextParser<BusinessMessagesDeleted> {
    @Override
    public Class<BusinessMessagesDeleted> getInputClass() {
        return BusinessMessagesDeleted.class;
    }

    @Override
    public String parse(BusinessMessagesDeleted t) throws TelegramObjectParseException {
        throwNotContainsStringException(t.getClass());
        return null;
    }
}
