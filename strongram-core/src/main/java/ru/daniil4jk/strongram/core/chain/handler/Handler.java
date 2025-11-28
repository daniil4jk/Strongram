package ru.daniil4jk.strongram.core.chain.handler;

import ru.daniil4jk.strongram.core.chain.context.Context;

import java.util.function.Consumer;

public interface Handler extends Consumer<Context> {
    void setNext(Handler next);
}
