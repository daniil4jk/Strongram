package ru.daniil4jk.strongram.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import ru.daniil4jk.strongram.TelegramUUID;
import ru.daniil4jk.strongram.parser.Parser;
import ru.daniil4jk.strongram.parser.uuid.TelegramUUIDParserService;

public abstract class TelegramUUIDParser<I extends BotApiObject> implements Parser<I, TelegramUUID> {
    {
        TelegramUUIDParserService.getInstance().addParser(this);
    }
}
