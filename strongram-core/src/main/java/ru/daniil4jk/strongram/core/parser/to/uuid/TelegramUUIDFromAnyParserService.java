package ru.daniil4jk.strongram.core.parser.to.uuid;

import lombok.Getter;
import ru.daniil4jk.strongram.core.dto.TelegramUUID;
import ru.daniil4jk.strongram.core.parser.to.FromAnyParserService;

public class TelegramUUIDFromAnyParserService extends FromAnyParserService<TelegramUUID> {
    @Getter
    private static final TelegramUUIDFromAnyParserService instance = new TelegramUUIDFromAnyParserService();

    private TelegramUUIDFromAnyParserService() {
    }

    @Override
    protected Class<TelegramUUID> getOutputClass() {
        return TelegramUUID.class;
    }
}
