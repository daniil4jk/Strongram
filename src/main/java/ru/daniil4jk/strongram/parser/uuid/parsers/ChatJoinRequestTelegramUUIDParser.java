package ru.daniil4jk.strongram.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.ChatJoinRequest;
import ru.daniil4jk.strongram.TelegramUUID;
import ru.daniil4jk.strongram.parser.TelegramObjectParseException;

public class ChatJoinRequestTelegramUUIDParser extends TelegramUUIDParser<ChatJoinRequest> {
    @Override
    public Class<ChatJoinRequest> getInputClass() {
        return ChatJoinRequest.class;
    }

    @Override
    public TelegramUUID parse(ChatJoinRequest t) throws TelegramObjectParseException {
        return new TelegramUUID(t.getChat(), t.getUser());
    }
}
