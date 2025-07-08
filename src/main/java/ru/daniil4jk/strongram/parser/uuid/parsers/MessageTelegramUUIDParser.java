package ru.daniil4jk.strongram.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.message.Message;
import ru.daniil4jk.strongram.TelegramUUID;

public class MessageTelegramUUIDParser extends TelegramUUIDParser<Message> {
    @Override
    public Class<Message> getParsingClass() {
        return Message.class;
    }

    @Override
    public TelegramUUID parse(Message t) {
        return new TelegramUUID(t.getChat(), t.getFrom());
    }
}
