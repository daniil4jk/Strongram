package ru.daniil4jk.strongram.core.parser.to.text.parser;

import org.telegram.telegrambots.meta.api.objects.message.Message;
import ru.daniil4jk.strongram.core.parser.TelegramObjectParseException;

public class MessageTextParser extends TextParser<Message> {
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
