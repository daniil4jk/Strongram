package ru.daniil4jk.strongram.core.unboxer.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import ru.daniil4jk.strongram.core.context.request.TelegramUUID;
import ru.daniil4jk.strongram.core.unboxer.parser.TelegramObjectParseException;

public class InlineQueryTelegramUUIDParser extends TelegramUUIDParser<InlineQuery> {
    @Override
    public Class<InlineQuery> getInputClass() {
        return InlineQuery.class;
    }

    @Override
    public TelegramUUID parse(InlineQuery t) throws TelegramObjectParseException {
        return new TelegramUUID(new Chat(t.getFrom().getId(), t.getChatType()), t.getFrom());
    }
}
