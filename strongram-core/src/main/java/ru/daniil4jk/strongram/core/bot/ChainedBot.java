package ru.daniil4jk.strongram.core.bot;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.core.chain.factory.ChainFactory;
import ru.daniil4jk.strongram.core.context.request.RequestContext;
import ru.daniil4jk.strongram.core.context.request.RequestContextImpl;
import ru.daniil4jk.strongram.core.downstream.CallbackWrapper;
import ru.daniil4jk.strongram.core.downstream.DownstreamHandler;
import ru.daniil4jk.strongram.core.response.dto.Response;
import ru.daniil4jk.strongram.core.response.responder.factory.ResponserFactory;
import ru.daniil4jk.strongram.core.response.responder.factory.ResponserFactoryImpl;
import ru.daniil4jk.strongram.core.response.sender.ResponseSink;
import ru.daniil4jk.strongram.core.upstream.UpstreamHandler;
import ru.daniil4jk.strongram.core.util.Lazy;

import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class ChainedBot extends BaseBot {
    private final Lazy<UpstreamHandler> upstreamChain = new Lazy<>(this::createUpstreamChain);
    private final CallbackWrapper downstreamWrapper = new CallbackWrapper(this::getDownstreamChain);
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
        return getUpstreamChain().get().build();
    }
    protected abstract ChainFactory<UpstreamHandler> getUpstreamChain();

    protected abstract DownstreamHandler[] getDownstreamChain();
}
