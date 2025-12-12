package ru.daniil4jk.strongram.core.unboxer.parser.text;

import lombok.Getter;
import ru.daniil4jk.strongram.core.unboxer.parser.ParserService;

public class TextParserService extends ParserService<String> {
    @Getter
    private static final TextParserService instance = new TextParserService();

    private TextParserService() {
    }

    @Override
    protected Class<String> getOutputClass() {
        return String.class;
    }
}
