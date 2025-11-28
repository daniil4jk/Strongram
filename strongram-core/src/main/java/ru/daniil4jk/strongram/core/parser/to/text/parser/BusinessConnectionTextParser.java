package ru.daniil4jk.strongram.core.parser.to.text.parser;

import org.telegram.telegrambots.meta.api.objects.business.BusinessConnection;
import ru.daniil4jk.strongram.core.parser.TelegramObjectParseException;

public class BusinessConnectionTextParser extends TextParser<BusinessConnection> {
    @Override
    public Class<BusinessConnection> getInputClass() {
        return BusinessConnection.class;
    }

    @Override
    public String parse(BusinessConnection t) throws TelegramObjectParseException {
        throwNotContainsStringException(t.getClass());
        return null;
    }
}
