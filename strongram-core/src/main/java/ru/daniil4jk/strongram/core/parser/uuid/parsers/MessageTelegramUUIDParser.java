package ru.daniil4jk.strongram.core.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.message.Message;
import ru.daniil4jk.strongram.core.TelegramUUID;
import ru.daniil4jk.strongram.core.parser.TelegramObjectParseException;

public class MessageTelegramUUIDParser extends TelegramUUIDParser<Message> {
    @Override
    public Class<Message> getInputClass() {
        return Message.class;
    }

    @Override
    public TelegramUUID parse(Message t) throws TelegramObjectParseException {
        return new TelegramUUID(t.getChat(), t.getFrom());
    }
}
