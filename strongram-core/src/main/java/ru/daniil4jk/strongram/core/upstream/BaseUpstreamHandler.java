package ru.daniil4jk.strongram.core.upstream;

import lombok.Setter;
import ru.daniil4jk.strongram.core.context.request.RequestContext;

public abstract class BaseUpstreamHandler implements UpstreamHandler {
    @Setter
    private UpstreamHandler next;

    @Override
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
