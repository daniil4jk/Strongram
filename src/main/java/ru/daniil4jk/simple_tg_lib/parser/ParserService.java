package ru.daniil4jk.simple_tg_lib.parser;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ParserService<O> {
    //Map<Class<I>, Parser<I, O>>. Type I in class == type I in parser.
    private final Map<Class<?>, Parser<?, O>> parserMap = new HashMap<>();

    public <I extends BotApiObject> ParserService(Collection<Parser<I, O>> parsers) {
        for (Parser<I, O> parser : parsers) {
            parserMap.put(parser.getParsingClass(), parser);
        }
    }

    public <I extends BotApiObject> O parse(I i) {
        return ((Parser<I, O>) parserMap.get(i.getClass())).parse(i);
    }
}
