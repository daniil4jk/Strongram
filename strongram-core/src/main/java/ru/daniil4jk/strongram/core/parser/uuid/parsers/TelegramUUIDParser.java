package ru.daniil4jk.strongram.core.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import ru.daniil4jk.strongram.core.TelegramUUID;
import ru.daniil4jk.strongram.core.parser.Parser;

public abstract class TelegramUUIDParser<I extends BotApiObject> implements Parser<I, TelegramUUID> {
    @Override
    public Class<TelegramUUID> getOutputClass() {
        return TelegramUUID.class;
    }
}
