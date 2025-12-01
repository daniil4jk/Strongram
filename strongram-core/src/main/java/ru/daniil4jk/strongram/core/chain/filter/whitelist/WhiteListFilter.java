package ru.daniil4jk.strongram.core.chain.filter.whitelist;

import lombok.RequiredArgsConstructor;
import ru.daniil4jk.strongram.core.chain.filter.Filter;

import java.util.function.Function;

@RequiredArgsConstructor
public abstract class WhiteListFilter<T> implements Filter {
    protected final Function<T, Filter> addPolicy;

    public abstract void add(T t);
    public abstract void remove(T t);
}
