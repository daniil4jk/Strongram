package ru.daniil4jk.strongram.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import ru.daniil4jk.strongram.TelegramUUID;
import ru.daniil4jk.strongram.parser.UpdateParser;
import ru.daniil4jk.strongram.parser.uuid.TelegramUUIDParserService;

public class UpdateTelegramUUIDParser extends UpdateParser<TelegramUUID> {
    {
        TelegramUUIDParserService.getInstance().addParser(this);
    }

    @Override
    protected <I extends BotApiObject> TelegramUUID parseInternal(I t) {
        return TelegramUUIDParserService.getInstance().parse(t);
    }
}
