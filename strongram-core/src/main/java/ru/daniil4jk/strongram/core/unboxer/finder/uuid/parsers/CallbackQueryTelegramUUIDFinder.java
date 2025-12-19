package ru.daniil4jk.strongram.core.unboxer.finder.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.daniil4jk.strongram.core.context.request.TelegramUUID;
import ru.daniil4jk.strongram.core.unboxer.finder.TelegramObjectFinderException;

public class CallbackQueryTelegramUUIDFinder extends TelegramUUIDFinder<CallbackQuery> {
    @Override
    public Class<CallbackQuery> getInputClass() {
        return CallbackQuery.class;
    }

    @Override
    public TelegramUUID parse(CallbackQuery t) throws TelegramObjectFinderException {
        return new TelegramUUID(t.getMessage().getChat(), t.getFrom());
    }
}
