package ru.daniil4jk.strongram.core.chain;

public interface ChainFactory<T> {
    ChainListCreator<T> call();
}
