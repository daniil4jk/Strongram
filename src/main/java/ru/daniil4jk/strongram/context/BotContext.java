package ru.daniil4jk.strongram.context;


public interface BotContext {
    boolean containsByClass(Class<?> clazz);
    boolean containsByName(String objectName);
    <T> T getByClass(Class<T> clazz);
    Object getByName(String objectName);
    <T> T getByNameCasted(String objectName, Class<T> clazz);
}
