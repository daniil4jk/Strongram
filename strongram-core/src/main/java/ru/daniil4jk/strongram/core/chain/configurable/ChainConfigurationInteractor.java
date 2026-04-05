package ru.daniil4jk.strongram.core.chain.configurable;

public interface ChainConfigurationInteractor<T> {
    void apply(ChainConfigurator<T> chainConfigurator);
}
