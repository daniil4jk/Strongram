package ru.daniil4jk.strongram.core.parser.to;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import ru.daniil4jk.strongram.core.parser.Parser;
import ru.daniil4jk.strongram.core.parser.SPIParserRegistry;
import ru.daniil4jk.strongram.core.parser.TelegramObjectParseException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class FromAnyParserService<O> {
    //Map<Class<I>, Parser<I, O>>
    private final Map<Class<?>, Parser<?, O>> parserMap = new HashMap<>();

    public FromAnyParserService(Collection<Parser<?, O>> parsers) {
        this();
        for (Parser<?, O> parser : parsers) {
            parserMap.put(parser.getInputClass(), parser);
        }
    }

    public FromAnyParserService() {
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
