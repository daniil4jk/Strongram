package ru.daniil4jk.strongram.core.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import ru.daniil4jk.strongram.core.TelegramUUID;
import ru.daniil4jk.strongram.core.parser.UpdateParser;
import ru.daniil4jk.strongram.core.parser.uuid.TelegramUUIDParserService;

public class UpdateTelegramUUIDParser extends UpdateParser<TelegramUUID> {
    @Override
    public Class<TelegramUUID> getOutputClass() {
        return TelegramUUID.class;
    }

    @Override
    protected <I extends BotApiObject> TelegramUUID parseInternal(I t) {
        return TelegramUUIDParserService.getInstance().parse(t);
    }
}
