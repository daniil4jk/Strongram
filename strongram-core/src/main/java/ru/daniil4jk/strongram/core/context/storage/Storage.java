package ru.daniil4jk.strongram.core.context.storage;

import java.util.Collection;

public interface Storage {
    <T> void set(String key, T object);
    <T> T get(String key);
    <T> T get(Class<T> classOfReturnValue, String key);
    <T> Collection<T> getCollection(String key);
    <T> Collection<T> getCollection(Class<T> classOfReturnEntryValue, String key);
}
