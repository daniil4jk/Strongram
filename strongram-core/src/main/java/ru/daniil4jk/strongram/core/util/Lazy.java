package ru.daniil4jk.strongram.core.util;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
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
                    try {
                        value = creator.get();
                    } catch (Exception e) {
                        log.error("Lazy initialization throws exception", e);
                    }
                }
            }
        }
    }

    public boolean isInitialized() {
        return value != null;
    }

    //Раньше get тупо возвращал value, но это сильно путало, поэтому теперь он работает так.
    //Нужно для совместимости с Supplier<>. Нахуя? В душе не ебу. Возможно когда-нибудь уберу.
    @Override
    public T get() {
        return initOrGet();
    }

    public T initOrGet() {
        initIfNeed();
        return get();
    }
}
