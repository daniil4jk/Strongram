package ru.daniil4jk.strongram.core.bot;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.core.chain.factory.ChainFactory;
import ru.daniil4jk.strongram.core.chain.handler.Handler;
import ru.daniil4jk.strongram.core.context.request.RequestContext;
import ru.daniil4jk.strongram.core.context.request.RequestContextImpl;
import ru.daniil4jk.strongram.core.response.dto.Response;
import ru.daniil4jk.strongram.core.response.responder.accumulating.ManagedAccumulatorResponder;
import ru.daniil4jk.strongram.core.util.Lazy;

import java.util.Collections;
import java.util.List;

@Slf4j
public abstract class ChainedBot extends BaseBot {
    private final Lazy<Handler> chain = new Lazy<>(this::createChain);

    public ChainedBot(String username) {
        super(username);
    }

    @Override
    public List<Response<?>> apply(Update update) {
        try (var responder = new ManagedAccumulatorResponder()) {
            RequestContext ctx = new RequestContextImpl(this, update, responder);
            chain.initOrGet().accept(ctx);
            return responder.getQueuedMessages();
        } catch (Exception e) {
            log.error("Error occurred while chain processing update", e);
            return Collections.emptyList();
        }
    }

    private Handler createChain() {
        return getChain().get().build();
    }

    protected abstract ChainFactory getChain();
}