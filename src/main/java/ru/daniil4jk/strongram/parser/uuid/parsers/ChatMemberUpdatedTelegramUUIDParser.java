package ru.daniil4jk.strongram.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberUpdated;
import ru.daniil4jk.strongram.TelegramUUID;

public class ChatMemberUpdatedTelegramUUIDParser extends TelegramUUIDParser<ChatMemberUpdated> {
    @Override
    public Class<ChatMemberUpdated> getParsingClass() {
        return ChatMemberUpdated.class;
    }

    @Override
    public TelegramUUID parse(ChatMemberUpdated t) {
        return new TelegramUUID(t.getChat(), t.getFrom());
    }
}
