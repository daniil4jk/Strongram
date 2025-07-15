package ru.daniil4jk.strongram.parser.payload.parser;

import org.telegram.telegrambots.meta.api.objects.message.Message;
import ru.daniil4jk.strongram.parser.TelegramObjectParseException;

public class MessagePayloadParser extends PayloadParser<Message> {
    @Override
    public Class<Message> getInputClass() {
        return Message.class;
    }

    @Override
    public String parse(Message t) throws TelegramObjectParseException {
        if (t.hasText()) return t.getText();
        if (t.hasCaption()) return t.getCaption();
        throw new TelegramObjectParseException("%s %s not contains text or caption"
                .formatted(t.getClass().getSimpleName(), t));
    }
}
