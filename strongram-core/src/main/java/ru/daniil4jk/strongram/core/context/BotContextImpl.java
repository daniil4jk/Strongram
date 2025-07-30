package ru.daniil4jk.strongram.core.context;

import lombok.Builder;
import lombok.Singular;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Map;

@Builder
public class BotContextImpl implements BotContext {
    @Singular("objectByClass")
    private final Map<Class<?>, Object> classMap;
    @Singular("objectByString")
    private final Map<String, Object> stringMap;

    public BotContextImpl(@Nullable Map<Class<?>, Object> classMap,
                          @Nullable Map<String, Object> stringMap) {
        if (classMap != null) {
            for (var entry : classMap.entrySet()) {
                if (entry.getValue() == null) continue;

                if (!entry.getKey().isInstance(entry.getValue())) {
                    throw new IllegalArgumentException("%s is`nt instanceof %s"
                            .formatted(entry.getValue().getClass(), entry.getKey()));
                }
            }
            this.classMap = Collections.unmodifiableMap(classMap);
        } else {
            this.classMap = null;
        }
        this.stringMap = stringMap != null ? Collections.unmodifiableMap(stringMap) : null;
    }

    @Override
    public boolean containsByClass(Class<?> clazz) {
        if (classMap == null) {
            return false;
        }
        return classMap.containsKey(clazz);
    }

    @Override
    public boolean containsByName(String objectName) {
        if (stringMap == null) {
            return false;
        }
        return stringMap.containsKey(objectName);
    }

    @Override
    public <T> T getByClass(Class<T> clazz) {
        if (classMap == null) {
            return null;
        }
        return (T) classMap.get(clazz);
    }

    @Override
    public Object getByName(String objectName) {
        if (stringMap == null) {
            return null;
        }
        return stringMap.get(objectName);
    }

    @Override
    public <T> T getByNameCasted(String objectName, Class<T> clazz) {
        try {
            if (stringMap == null) {
                return null;
            }
            return (T) stringMap.get(objectName);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("object by key %s is`nt instanceof %s"
                    .formatted(objectName, clazz));
        }
    }
}