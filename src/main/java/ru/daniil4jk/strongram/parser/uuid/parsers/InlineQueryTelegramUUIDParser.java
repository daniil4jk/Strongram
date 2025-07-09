package ru.daniil4jk.strongram.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import ru.daniil4jk.strongram.TelegramUUID;
import ru.daniil4jk.strongram.parser.TelegramObjectParseException;

public class InlineQueryTelegramUUIDParser extends TelegramUUIDParser<InlineQuery> {
    @Override
    public Class<InlineQuery> getParsingClass() {
        return InlineQuery.class;
    }

    @Override
    public TelegramUUID parse(InlineQuery t) throws TelegramObjectParseException {
        return new TelegramUUID(new Chat(t.getFrom().getId(), t.getChatType()), t.getFrom());
    }
}
