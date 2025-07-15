package ru.daniil4jk.strongram.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.business.BusinessConnection;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import ru.daniil4jk.strongram.TelegramUUID;
import ru.daniil4jk.strongram.parser.TelegramObjectParseException;

public class BusinessConnectionTelegramUUIDParser extends TelegramUUIDParser<BusinessConnection> {
    @Override
    public Class<BusinessConnection> getInputClass() {
        return BusinessConnection.class;
    }

    @Override
    public TelegramUUID parse(BusinessConnection t) throws TelegramObjectParseException {
        return new TelegramUUID(new Chat(t.getUserChatId(), "private"), t.getUser());
    }
}
