package ru.daniil4jk.strongram.core.chain.handler;

import lombok.Setter;
import ru.daniil4jk.strongram.core.chain.context.RequestContext;

public abstract class BaseHandler implements Handler{
    @Setter
    private Handler next;

    public void accept(RequestContext ctx) {
        process(ctx);
    }

    protected abstract void process(RequestContext ctx);

    protected final void processNext(RequestContext ctx) {
        if (next != null) {
            next.accept(ctx);
        }
    }
}
