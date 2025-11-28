package ru.daniil4jk.strongram.core.parser.to.text.parser;

import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberUpdated;
import ru.daniil4jk.strongram.core.parser.TelegramObjectParseException;

public class ChatMemberUpdatedTextParser extends TextParser<ChatMemberUpdated> {
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
