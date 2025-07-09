package ru.daniil4jk.strongram.parser.payload.parser;

import org.telegram.telegrambots.meta.api.objects.ChatJoinRequest;
import ru.daniil4jk.strongram.parser.TelegramObjectParseException;

public class ChatJoinRequestPayloadParser extends PayloadParser<ChatJoinRequest> {
    @Override
    public Class<ChatJoinRequest> getParsingClass() {
        return ChatJoinRequest.class;
    }

    @Override
    public String parse(ChatJoinRequest t) throws TelegramObjectParseException {
        return t.getBio();
    }
}
