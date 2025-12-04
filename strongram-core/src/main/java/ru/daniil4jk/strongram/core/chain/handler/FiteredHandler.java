package ru.daniil4jk.strongram.core.chain.handler;

import ru.daniil4jk.strongram.core.chain.context.RequestContext;
import ru.daniil4jk.strongram.core.chain.filter.Filter;

public abstract class FiteredHandler extends BaseHandler {
    protected abstract Filter getFilter();

    @Override
    protected void process(RequestContext ctx) {
        if (getFilter().test(ctx)) {
            processFiltered(ctx);
        } else {
            processNext(ctx);
        }
    }

    protected void processFiltered(RequestContext ctx) {

    }
}
