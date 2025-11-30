package ru.daniil4jk.strongram.core.chain.filter;

import lombok.RequiredArgsConstructor;
import ru.daniil4jk.strongram.core.chain.context.Context;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

@RequiredArgsConstructor
public class WhiteListFilter<T> implements Filter {
    private final Function<T, Filter> addPolicy;

    private final Set<T> accepted = new HashSet<>();
    private final AtomicReference<Filter> inherit = new AtomicReference<>(Filters.rejectAll());

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
