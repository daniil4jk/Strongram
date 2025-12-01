package ru.daniil4jk.strongram.core.chain.handler;

import lombok.Setter;
import ru.daniil4jk.strongram.core.chain.context.Context;
import ru.daniil4jk.strongram.core.chain.filter.Filter;
import ru.daniil4jk.strongram.core.chain.filter.Filters;

public abstract class BaseHandler implements Handler {
    @Setter
    private Handler next;

    @Override
    public final void accept(Context ctx) {
        if (getFilter().test(ctx)) {
            process(ctx);
        } else {
            processNext(ctx);
        }
    }

    protected Filter getFilter() {
        return Filters.acceptAll();
    }

    protected void process(Context ctx) {
        processNext(ctx);
    }

    protected final void processNext(Context ctx) {
        if (next != null) {
            next.accept(ctx);
        }
    }
}
