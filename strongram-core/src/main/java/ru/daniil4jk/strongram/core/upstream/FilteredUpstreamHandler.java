package ru.daniil4jk.strongram.core.upstream;

import org.jetbrains.annotations.NotNull;
import ru.daniil4jk.strongram.core.context.request.RequestContext;
import ru.daniil4jk.strongram.core.filter.Filter;

public abstract class FilteredUpstreamHandler extends BaseUpstreamHandler {
    @Override
    protected final void process(RequestContext ctx) {
        if (getFilter().test(ctx)) {
            processFiltered(ctx);
        } else {
            processNext(ctx);
        }
    }

    protected abstract @NotNull Filter getFilter();

    protected abstract void processFiltered(@NotNull RequestContext ctx);
}
