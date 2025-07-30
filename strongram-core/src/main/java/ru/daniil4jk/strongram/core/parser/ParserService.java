package ru.daniil4jk.strongram.core.parser;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class ParserService<O> {
    //Map<Class<I>, Parser<I, O>>. Type I in class == type I in parser.
    private final Map<Class<?>, Parser<?, O>> parserMap = new HashMap<>();

    public <I extends BotApiObject> ParserService(Collection<Parser<I, O>> parsers) {
        this();
        for (Parser<I, O> parser : parsers) {
            parserMap.put(parser.getInputClass(), parser);
        }
    }

    public ParserService() {
        for (var parser : SPIParserRegistry.getInstance().getByOutputClass(getOutputClass())) {
            parserMap.put(parser.getInputClass(), parser);
        }
    }

    public <I extends BotApiObject> void addParser(Parser<I, O> parser) {
        parserMap.put(parser.getInputClass(), parser);
    }

    @SuppressWarnings("unchecked")
    public <I extends BotApiObject> O parse(I i) throws TelegramObjectParseException {
        return ((Parser<I, O>) Optional.ofNullable(parserMap.get(i.getClass()))
                .orElseThrow(() ->
                        new TelegramObjectParseException("Not enough parser for types I: %s, O: %s"
                                .formatted(i.getClass().getName(), getOutputClass().getName()))))
                .parse(i);
    }

    protected abstract Class<O> getOutputClass();
}
