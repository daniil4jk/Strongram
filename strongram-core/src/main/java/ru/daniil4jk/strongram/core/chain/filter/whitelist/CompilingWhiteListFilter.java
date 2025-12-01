package ru.daniil4jk.strongram.core.chain.filter.whitelist;

import ru.daniil4jk.strongram.core.chain.context.Context;
import ru.daniil4jk.strongram.core.chain.filter.Filter;
import ru.daniil4jk.strongram.core.chain.filter.Filters;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class CompilingWhiteListFilter<T> extends WhiteListFilter<T> {
    private final Set<T> accepted = new HashSet<>();
    private final AtomicReference<Filter> inherit = new AtomicReference<>(Filters.rejectAll());

    public CompilingWhiteListFilter(Function<T, Filter> addPolicy) {
        super(addPolicy);
    }

    public void add(T s) {
        accepted.add(s);
        recountInherit();
    }

    public void remove(T t) {
        accepted.remove(t);
        recountInherit();
    }

    private void recountInherit() {
        Filter newFilter = null;

        for (T t : accepted) {
            if (newFilter == null) {
                newFilter = addPolicy.apply(t);
            } else {
                newFilter = newFilter.and(addPolicy.apply(t));
            }
        }

        inherit.set(newFilter);
    }

    @Override
    public boolean test(Context ctx) {
        return inherit.get().test(ctx);
    }
}
