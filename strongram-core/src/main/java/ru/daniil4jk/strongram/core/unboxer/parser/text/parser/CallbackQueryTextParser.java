package ru.daniil4jk.strongram.core.unboxer.parser.text.parser;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.daniil4jk.strongram.core.unboxer.parser.TelegramObjectParseException;

public class CallbackQueryTextParser extends TextParser<CallbackQuery> {
    @Override
    public Class<CallbackQuery> getInputClass() {
        return CallbackQuery.class;
    }

    @Override
    public String parse(CallbackQuery t) throws TelegramObjectParseException {
        return t.getData();
    }
}
