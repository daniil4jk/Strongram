package ru.daniil4jk.strongram.core.chain.factory.configurable;

import ru.daniil4jk.strongram.core.chain.Chain;
import ru.daniil4jk.strongram.core.chain.NextConsumer;

public interface ChainConfigurator<T extends NextConsumer<T>> {
    void configure(Chain<T> chain);
}
