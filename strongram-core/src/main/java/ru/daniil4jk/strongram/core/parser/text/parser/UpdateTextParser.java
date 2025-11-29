package ru.daniil4jk.strongram.core.parser.text.parser;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import ru.daniil4jk.strongram.core.parser.UpdateParser;
import ru.daniil4jk.strongram.core.parser.text.TextParserService;

public class UpdateTextParser extends UpdateParser<String> {
    @Override
    public Class<String> getOutputClass() {
        return String.class;
    }

    @Override
    protected <I extends BotApiObject> String parseInternal(I t) {
        return TextParserService.getInstance().parse(t);
    }
}
