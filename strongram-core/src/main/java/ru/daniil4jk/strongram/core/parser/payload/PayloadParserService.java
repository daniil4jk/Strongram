package ru.daniil4jk.strongram.core.parser.payload;

import lombok.Getter;
import ru.daniil4jk.strongram.core.parser.ParserService;

public class PayloadParserService extends ParserService<String> {
    @Getter
    private static final PayloadParserService instance = new PayloadParserService();

    private PayloadParserService() {
    }

    @Override
    protected Class<String> getOutputClass() {
        return String.class;
    }
}
