package ru.daniil4jk.strongram.core.util;

import lombok.Getter;

import java.util.function.Supplier;

public class Lazy<T> implements Supplier<T> {
    private final Supplier<T> creator;
    private volatile T value;
    @Getter
    private volatile boolean initialized;

    public Lazy(Supplier<T> creator) {
        this.creator = creator;
    }

    public void initIfNeed() {
        if (!isInitialized()) {
            synchronized (this) {
                if (!isInitialized()) {
                    initialized = true;
                    value = creator.get();
                }
            }
        }
    }

    @Override
    public T get() {
        return value;
    }

    public T initOrGet() {
        initIfNeed();
        return get();
    }
}
