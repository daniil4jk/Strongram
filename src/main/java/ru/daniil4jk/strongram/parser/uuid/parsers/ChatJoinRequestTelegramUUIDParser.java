package ru.daniil4jk.strongram.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.ChatJoinRequest;
import ru.daniil4jk.strongram.TelegramUUID;

public class ChatJoinRequestTelegramUUIDParser extends TelegramUUIDParser<ChatJoinRequest> {
    @Override
    public Class<ChatJoinRequest> getParsingClass() {
        return ChatJoinRequest.class;
    }

    @Override
    public TelegramUUID parse(ChatJoinRequest t) {
        return new TelegramUUID(t.getChat(), t.getUser());
    }
}
