package ru.daniil4jk.strongram.core.chain.factory;

import ru.daniil4jk.strongram.core.chain.Chain;
import ru.daniil4jk.strongram.core.chain.factory.configurable.ChainConfigurator;

public class ConfigurableChainFactory implements ChainFactory {
    private static final ChainFactory DEFAULT_INTERNAL_FACTORY = Chain::new;

    private final ChainFactory internalFactory;
    private final ChainConfigurator configurator;

    public ConfigurableChainFactory(ChainConfigurator configurator) {
        this(DEFAULT_INTERNAL_FACTORY, configurator);
    }

    public ConfigurableChainFactory(ChainFactory internalFactory, ChainConfigurator configurator) {
        this.internalFactory = internalFactory;
        this.configurator = configurator;
    }

    @Override
    public Chain get() {
        Chain chain = internalFactory.get();
        configurator.configure(chain);
        return chain;
    }
}
