package ru.daniil4jk.strongram.core.bot;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.core.chain.Chain;
import ru.daniil4jk.strongram.core.chain.configurable.ChainConfigurator;
import ru.daniil4jk.strongram.core.chain.ChainFactory;
import ru.daniil4jk.strongram.core.chain.configurable.ConfigurableChainFactory;
import ru.daniil4jk.strongram.core.context.request.RequestContext;
import ru.daniil4jk.strongram.core.context.request.RequestContextImpl;
import ru.daniil4jk.strongram.core.downstream.CallbackWrapper;
import ru.daniil4jk.strongram.core.downstream.DownstreamHandler;
import ru.daniil4jk.strongram.core.response.responder.factory.ResponserFactory;
import ru.daniil4jk.strongram.core.response.responder.factory.ResponserFactoryImpl;
import ru.daniil4jk.strongram.core.response.sender.ResponseSink;
import ru.daniil4jk.strongram.core.upstream.UpstreamHandler;
import ru.daniil4jk.strongram.core.upstream.preinstalled.CannotProcessUpstreamHandler;
import ru.daniil4jk.strongram.core.util.Lazy;

import java.util.List;

@Slf4j
public abstract class ChainedBot extends BaseBot {
    private final Lazy<UpstreamHandler> upstreamChain = new Lazy<>(this::createUpstreamChain);
    private final Lazy<List<DownstreamHandler>> downstreamList = new Lazy<>(this::createDownstreamList);
    private final CallbackWrapper downstreamWrapper = new CallbackWrapper(downstreamList);
    private final ResponserFactory responserFactory = new ResponserFactoryImpl();

    public ChainedBot(@Nullable String username) {
        super(username);
    }

    @Override
    public void accept(Update update, ResponseSink tempCallback) {
        try {
            RequestContext ctx = new RequestContextImpl(this, update, responserFactory);
            responserFactory.setTempCallback(downstreamWrapper.wrap(ctx, tempCallback));
            upstreamChain.initOrGet().accept(ctx);
        } catch (Exception e) {
            log.error("Error occurred while chain processing update", e);
        } finally {
            responserFactory.resetTempCallback();
        }
    }

    @Override
    public void setDefaultCallback(ResponseSink defaultCallback) {
        defaultCallback = downstreamWrapper.wrap(defaultCallback);
        super.setDefaultCallback(defaultCallback);
        responserFactory.setPermanentCallback(defaultCallback);
    }

    private UpstreamHandler createUpstreamChain() {
        return new Chain<>(getUpstreamFactory().call()).build();
    }

    protected ChainFactory<UpstreamHandler> getUpstreamFactory() {
        return new ConfigurableChainFactory<>(this::configureUpstream);
    }

    protected void configureUpstream(ChainConfigurator<UpstreamHandler> chain) {
        chain.add(new CannotProcessUpstreamHandler());
    }

    private List<DownstreamHandler> createDownstreamList() {
        return getDownstreamFactory().call().getResultAsList();
    }

    protected ChainFactory<DownstreamHandler> getDownstreamFactory() {
        return new ConfigurableChainFactory<>(this::configureDownstream);
    }

    protected void configureDownstream(ChainConfigurator<DownstreamHandler> chain) {

    }
}
