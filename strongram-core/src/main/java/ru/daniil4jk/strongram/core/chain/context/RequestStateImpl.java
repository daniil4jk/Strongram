package ru.daniil4jk.strongram.core.chain.context;

import ru.daniil4jk.strongram.core.util.Lazy;

import java.util.HashMap;
import java.util.Map;

public class RequestStateImpl implements RequestState {
    private final Lazy<Map<Class<?>, Object>> byClassMap = new Lazy<>(HashMap::new);
    private final Lazy<Map<String, Object>> byNameMap = new Lazy<>(HashMap::new);

    @Override
    public <T> void setByClass(Class<T> newKey, T newValue) {
        byClassMap.initOrGet().compute(newKey,
                (existingKey, existingValue) -> {
                    if (existingValue != null) {
                        throwKeyExistException(existingKey.getName(), existingValue);
                    }
                    return newValue;
                }
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getByClass(Class<T> key) {
        if (!byClassMap.isInitialized()) return null;
        return (T) byClassMap.get().get(key);
    }

    @Override
    public <T> void setByName(String newKey, T newValue) {
        byNameMap.initOrGet().compute(newKey,
                (existingKey, existingValue) -> {
                    if (existingValue != null) {
                        throwKeyExistException(existingKey, existingValue);
                    }
                    return newValue;
                }
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getByName(Class<T> clazz, String key) {
        if (!byNameMap.isInitialized()) return null;
        return (T) byNameMap.get().get(key);
    }

    private void throwKeyExistException(String keyName, Object value) {
        throw new IllegalArgumentException(
                "Key %s already exist in map with value %s"
                        .formatted(keyName, value)
        );
    }
}
