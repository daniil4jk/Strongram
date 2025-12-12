package ru.daniil4jk.strongram.core.unboxer.parser.text.parser;

import org.telegram.telegrambots.meta.api.objects.ChatJoinRequest;
import ru.daniil4jk.strongram.core.unboxer.parser.TelegramObjectParseException;

public class ChatJoinRequestTextParser extends TextParser<ChatJoinRequest> {
    @Override
    public Class<ChatJoinRequest> getInputClass() {
        return ChatJoinRequest.class;
    }

    @Override
    public String parse(ChatJoinRequest t) throws TelegramObjectParseException {
        return t.getBio();
    }
}
