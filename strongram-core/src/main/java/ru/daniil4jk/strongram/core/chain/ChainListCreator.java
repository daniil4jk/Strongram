package ru.daniil4jk.strongram.core.chain;

import java.util.List;

public interface ChainListCreator<T> {
    List<T> getResultAsList();
}
