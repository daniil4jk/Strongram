package ru.daniil4jk.strongram.core.chain.configurable;

import ru.daniil4jk.strongram.core.chain.ChainFactory;

public class ConfigurableChainFactory<T> implements ChainFactory<T> {
    private final ChainConfigurationInteractor<T> userConfiguration;

    public ConfigurableChainFactory(ChainConfigurationInteractor<T> userConfiguration) {
        this.userConfiguration = userConfiguration;
    }

    @Override
    public ChainConfigurator<T> call() {
        ChainConfigurator<T> chainConfigurator = new ChainConfigurator<>();
        userConfiguration.apply(chainConfigurator);
        return chainConfigurator;
    }
}
