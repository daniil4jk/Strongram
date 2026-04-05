package ru.daniil4jk.strongram.core.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.function.Supplier;

@ToString
@EqualsAndHashCode
public class Lazy<T> implements Supplier<T> {
    private final Supplier<T> creator;
    private volatile T value;

    public Lazy(Supplier<T> creator) {
        this.creator = creator;
    }

    public void initIfNeed() {
        if (!isInitialized()) {
            synchronized (this) {
                if (!isInitialized()) {
                    value = creator.get();
                }
            }
        }
    }

    public boolean isInitialized() {
        return value != null;
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
