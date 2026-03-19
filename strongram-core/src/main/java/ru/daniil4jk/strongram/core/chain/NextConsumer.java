package ru.daniil4jk.strongram.core.chain;

public interface NextConsumer<T> {
    void setNext(T next);
}
