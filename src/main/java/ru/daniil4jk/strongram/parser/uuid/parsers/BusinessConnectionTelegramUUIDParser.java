package ru.daniil4jk.strongram.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.business.BusinessConnection;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import ru.daniil4jk.strongram.TelegramUUID;

public class BusinessConnectionTelegramUUIDParser extends TelegramUUIDParser<BusinessConnection> {
    @Override
    public Class<BusinessConnection> getParsingClass() {
        return BusinessConnection.class;
    }

    @Override
    public TelegramUUID parse(BusinessConnection t) {
        return new TelegramUUID(new Chat(t.getUserChatId(), "private"), t.getUser());
    }
}
