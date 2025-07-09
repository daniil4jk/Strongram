package ru.daniil4jk.strongram.parser;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class ParserService<O> {
    //Map<Class<I>, Parser<I, O>>. Type I in class == type I in parser.
    private final Map<Class<?>, Parser<?, O>> parserMap = new HashMap<>();

    public ParserService() {}

    public <I extends BotApiObject> ParserService(Collection<Parser<I, O>> parsers) {
        for (Parser<I, O> parser : parsers) {
            parserMap.put(parser.getParsingClass(), parser);
        }
    }

    public <I extends BotApiObject> void addParser(Parser<I, O> parser) {
        parserMap.put(parser.getParsingClass(), parser);
    }

    @SuppressWarnings("unchecked")
    public <I extends BotApiObject> O parse(I i) throws TelegramObjectParseException {
        return ((Parser<I, O>) Optional.ofNullable(parserMap.get(i.getClass()))
                .orElseThrow(() ->
                        new TelegramObjectParseException("Not enough parser for types I: %s, O: %s"
                                .formatted(getReturnsClass().getName(), i.getClass().getName()))))
                .parse(i);
    }

    protected abstract Class<O> getReturnsClass();
}
