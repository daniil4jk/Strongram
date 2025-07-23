package ru.daniil4jk.strongram.parser.uuid;

import lombok.Getter;
import ru.daniil4jk.strongram.TelegramUUID;
import ru.daniil4jk.strongram.parser.ParserService;

public class TelegramUUIDParserService extends ParserService<TelegramUUID> {
    @Getter
    private static final TelegramUUIDParserService instance = new TelegramUUIDParserService();

    private TelegramUUIDParserService() {
    }

    @Override
    protected Class<TelegramUUID> getOutputClass() {
        return TelegramUUID.class;
    }
}
