package ru.daniil4jk.strongram.core.chain.handler;

import org.jetbrains.annotations.NotNull;
import ru.daniil4jk.strongram.core.chain.context.RequestContext;
import ru.daniil4jk.strongram.core.chain.filter.Filter;

public abstract class FilteredHandler extends BaseHandler {
    @Override
    protected final void process(RequestContext ctx) {
        if (getFilter().test(ctx)) {
            processFiltered(ctx);
        } else {
            processNext(ctx);
        }
    }

    protected abstract @NotNull Filter getFilter();

    protected abstract void processFiltered(RequestContext ctx);
}
