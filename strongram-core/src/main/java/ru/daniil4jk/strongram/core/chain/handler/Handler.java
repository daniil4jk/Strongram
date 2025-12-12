package ru.daniil4jk.strongram.core.chain.handler;

import ru.daniil4jk.strongram.core.context.request.RequestContext;

import java.util.function.Consumer;

public interface Handler extends Consumer<RequestContext> {
    void setNext(Handler next);
}
