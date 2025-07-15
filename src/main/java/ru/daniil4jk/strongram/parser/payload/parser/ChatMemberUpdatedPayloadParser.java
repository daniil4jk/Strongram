package ru.daniil4jk.strongram.parser.payload.parser;

import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberUpdated;
import ru.daniil4jk.strongram.parser.TelegramObjectParseException;

public class ChatMemberUpdatedPayloadParser extends PayloadParser<ChatMemberUpdated> {
    @Override
    public Class<ChatMemberUpdated> getInputClass() {
        return ChatMemberUpdated.class;
    }

    @Override
    public String parse(ChatMemberUpdated t) throws TelegramObjectParseException {
        throwNotContainsStringException(t.getClass());
        return null;
    }
}
