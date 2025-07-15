package ru.daniil4jk.strongram.parser.payload.parser;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.daniil4jk.strongram.parser.TelegramObjectParseException;

public class CallbackQueryPayloadParser extends PayloadParser<CallbackQuery> {
    @Override
    public Class<CallbackQuery> getInputClass() {
        return CallbackQuery.class;
    }

    @Override
    public String parse(CallbackQuery t) throws TelegramObjectParseException {
        return t.getData();
    }
}
