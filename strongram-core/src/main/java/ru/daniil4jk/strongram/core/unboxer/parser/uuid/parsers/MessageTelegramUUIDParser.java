package ru.daniil4jk.strongram.core.unboxer.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.message.Message;
import ru.daniil4jk.strongram.core.context.request.TelegramUUID;
import ru.daniil4jk.strongram.core.unboxer.parser.TelegramObjectParseException;

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
