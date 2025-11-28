package ru.daniil4jk.strongram.core.chain.context;

public interface RequestState {
    <T> void setByClass(Class<T> clazz, T object);
    <T> T getByClass(Class<T> clazz);
    <T> void setByName(String name, T object);
    <T> T getByName(Class<T> clazz, String name);
}
