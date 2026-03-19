package ru.daniil4jk.strongram.core.chain.factory;

import ru.daniil4jk.strongram.core.chain.Chain;
import ru.daniil4jk.strongram.core.chain.NextConsumer;

public interface ChainFactory<T extends NextConsumer<T>> {
    Chain<T> get();
}
