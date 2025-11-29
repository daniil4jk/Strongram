package ru.daniil4jk.strongram.core.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.daniil4jk.strongram.core.dto.TelegramUUID;
import ru.daniil4jk.strongram.core.parser.TelegramObjectParseException;

public class CallbackQueryTelegramUUIDParser extends TelegramUUIDParser<CallbackQuery> {
    @Override
    public Class<CallbackQuery> getInputClass() {
        return CallbackQuery.class;
    }

    @Override
    public TelegramUUID parse(CallbackQuery t) throws TelegramObjectParseException {
        return new TelegramUUID(t.getMessage().getChat(), t.getFrom());
    }
}
