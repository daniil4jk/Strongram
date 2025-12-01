package ru.daniil4jk.strongram.core.chain.filter.whitelist;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.daniil4jk.strongram.core.chain.context.Context;
import ru.daniil4jk.strongram.core.chain.filter.Filter;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;

public class HotUpdateableWhiteListFilter<T> extends WhiteListFilter<T> {
    private final Checker checker;
    private final Map<T, Filter> filters = new HashMap<>();

    public HotUpdateableWhiteListFilter(Function<T, Filter> addPolicy) {
        super(addPolicy);

        checker = new Checker();
    }

    public HotUpdateableWhiteListFilter(Function<T, Filter> addPolicy, @NotNull ForkJoinPool pool) {
        super(addPolicy);

        checker = new ParallelChecker(pool);
    }

    @Override
    public void add(T t) {
        filters.put(t, addPolicy.apply(t));
    }

    @Override
    public void remove(T t) {
        filters.remove(t);
    }

    @Override
    public boolean test(Context ctx) {
        return checker.apply(ctx);
    }

    @AllArgsConstructor
    private class ParallelChecker extends Checker {
        private final ForkJoinPool pool;

        @Override
        public Boolean apply(Context ctx) {
            try {
                return pool.submit(
                        () -> filters.values()
                                .stream()
                                .parallel()
                                .anyMatch(filter -> filter.test(ctx))
                ).get();
            } catch (InterruptedException | ExecutionException e) {
                return super.apply(ctx);
            }
        }
    }

    private class Checker implements Function<Context, Boolean> {
        @Override
        public Boolean apply(Context ctx) {
            return filters.values()
                    .stream()
                    .anyMatch(filter -> filter.test(ctx));
        }
    }
}
