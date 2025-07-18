package ru.daniil4jk.strongram.parser.payload.parser;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import ru.daniil4jk.strongram.parser.UpdateParser;
import ru.daniil4jk.strongram.parser.payload.PayloadParserService;

public class UpdatePayloadParser extends UpdateParser<String> {
    @Override
    public Class<String> getOutputClass() {
        return String.class;
    }

    @Override
    protected <I extends BotApiObject> String parseInternal(I t) {
        return PayloadParserService.getInstance().parse(t);
    }
}
