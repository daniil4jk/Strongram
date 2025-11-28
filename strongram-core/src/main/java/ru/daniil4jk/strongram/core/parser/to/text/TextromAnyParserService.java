package ru.daniil4jk.strongram.core.parser.to.text;

import lombok.Getter;
import ru.daniil4jk.strongram.core.parser.to.FromAnyParserService;

public class TextromAnyParserService extends FromAnyParserService<String> {
    @Getter
    private static final TextromAnyParserService instance = new TextromAnyParserService();

    private TextromAnyParserService() {
    }

    @Override
    protected Class<String> getOutputClass() {
        return String.class;
    }
}
