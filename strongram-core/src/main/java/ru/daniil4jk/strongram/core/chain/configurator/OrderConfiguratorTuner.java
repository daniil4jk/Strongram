package ru.daniil4jk.strongram.core.chain.configurator;

public interface OrderConfiguratorTuner<T> {
    void apply(OrderConfigurator<T> orderConfigurator);
}
