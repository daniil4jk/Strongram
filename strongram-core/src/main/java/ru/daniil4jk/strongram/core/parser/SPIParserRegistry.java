package ru.daniil4jk.strongram.core.parser;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;

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

    @SuppressWarnings("unchecked")
    public <I extends BotApiObject> Collection<? extends Parser<I, ?>> getByInputClass(Class<I> inputClass) {
        return loader.stream()
                .filter(p -> Objects.equals(p.get().getInputClass(), inputClass))
                .map(parserProvider -> (Parser<I, ? extends BotApiMethod<?>>) parserProvider.get())
                .collect(Collectors.toList());
    }
}
