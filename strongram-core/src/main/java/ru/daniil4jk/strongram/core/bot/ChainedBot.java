package ru.daniil4jk.strongram.core.bot;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.core.chain.factory.ChainFactory;
import ru.daniil4jk.strongram.core.context.request.RequestContext;
import ru.daniil4jk.strongram.core.context.request.RequestContextImpl;
import ru.daniil4jk.strongram.core.handler.Handler;
import ru.daniil4jk.strongram.core.response.responder.factory.ResponserFactory;
import ru.daniil4jk.strongram.core.response.responder.factory.ResponserFactoryImpl;
import ru.daniil4jk.strongram.core.response.sender.ResponseSink;
import ru.daniil4jk.strongram.core.util.Lazy;

@Slf4j
public abstract class ChainedBot extends BaseBot {
    private final Lazy<Handler> chain = new Lazy<>(this::createChain);
    private final ResponserFactory responserFactory = new ResponserFactoryImpl();

    public ChainedBot(String username) {
        super(username);
    }

    @Override
    public void accept(Update update, ResponseSink tempCallback) {
        try {
            responserFactory.setTempCallback(tempCallback);
            RequestContext ctx = new RequestContextImpl(this, update, responserFactory);
            chain.initOrGet().accept(ctx);
        } catch (Exception e) {
            log.error("Error occurred while chain processing update", e);
        } finally {
            responserFactory.setTempCallback(getDefaultCallback());
        }
    }

    private Handler createChain() {
        return getChain().get().build();
    }

    @Override
    public void setDefaultCallback(ResponseSink defaultCallback) {
        super.setDefaultCallback(defaultCallback);
        responserFactory.setPermanentCallback(defaultCallback);
    }

    protected abstract ChainFactory getChain();
}
