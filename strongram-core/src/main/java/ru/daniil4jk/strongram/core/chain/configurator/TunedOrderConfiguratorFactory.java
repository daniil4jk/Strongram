package ru.daniil4jk.strongram.core.chain.configurator;

public class TunedOrderConfiguratorFactory<T> {
    private final OrderConfiguratorTuner<T> userConfiguration;

    public TunedOrderConfiguratorFactory(OrderConfiguratorTuner<T> userConfiguration) {
        this.userConfiguration = userConfiguration;
    }

    public OrderConfigurator<T> get() {
        OrderConfigurator<T> chainOrderConfigurator = new OrderConfigurator<>();
        userConfiguration.apply(chainOrderConfigurator);
        return chainOrderConfigurator;
    }
}
