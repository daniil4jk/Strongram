package ru.daniil4jk.strongram.parser;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

import java.util.Collection;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public class SPIParserRegistry {
    private static final SPIParserRegistry me = new SPIParserRegistry();
    private static final ServiceLoader<Parser> loader = ServiceLoader.load(Parser.class);

    private SPIParserRegistry() {
    }

    public static SPIParserRegistry getInstance() {
        return me;
    }

    @SuppressWarnings("unchecked")
    public <O> Collection<? extends Parser<?, O>> getByOutputClass(Class<O> outputClass) {
        return loader.stream()
                .filter(p -> Objects.equals(p.get().getOutputClass(), outputClass))
                .map(parserProvider -> (Parser<? extends BotApiObject, O>) parserProvider.get())
                .collect(Collectors.toList());
    }
}
