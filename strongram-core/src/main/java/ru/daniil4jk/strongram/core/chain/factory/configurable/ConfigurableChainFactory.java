package ru.daniil4jk.strongram.core.chain.factory.configurable;

import ru.daniil4jk.strongram.core.chain.Chain;
import ru.daniil4jk.strongram.core.chain.NextConsumer;
import ru.daniil4jk.strongram.core.chain.factory.ChainFactory;

public class ConfigurableChainFactory<T extends NextConsumer<T>> implements ChainFactory<T> {
    private static final ChainFactory DEFAULT_INTERNAL_FACTORY = Chain::new;

    private final ChainFactory<T> internalFactory;
    private final ChainConfigurator configurator;

    public ConfigurableChainFactory(ChainConfigurator configurator) {
        this(DEFAULT_INTERNAL_FACTORY, configurator);
    }

    public ConfigurableChainFactory(ChainFactory<T> internalFactory, ChainConfigurator configurator) {
        this.internalFactory = internalFactory;
        this.configurator = configurator;
    }

    @Override
    public Chain<T> get() {
        Chain<T> chain = internalFactory.get();
        configurator.configure(chain);
        return chain;
    }
}
