package ru.daniil4jk.strongram.core.chain.handler;

import lombok.Setter;
import ru.daniil4jk.strongram.core.chain.context.RequestContext;
import ru.daniil4jk.strongram.core.chain.filter.Filter;
import ru.daniil4jk.strongram.core.chain.filter.Filters;

public abstract class BaseHandler implements Handler {
    @Setter
    private Handler next;

    @Override
    public final void accept(RequestContext ctx) {
        if (getFilter().test(ctx)) {
            process(ctx);
        } else {
            processNext(ctx);
        }
    }

    protected Filter getFilter() {
        return Filters.acceptAll();
    }

    protected void process(RequestContext ctx) {
        processNext(ctx);
    }

    protected final void processNext(RequestContext ctx) {
        if (next != null) {
            next.accept(ctx);
        }
    }
}
