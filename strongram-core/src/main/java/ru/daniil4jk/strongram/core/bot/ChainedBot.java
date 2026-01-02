package ru.daniil4jk.strongram.core.bot;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.core.chain.factory.ChainFactory;
import ru.daniil4jk.strongram.core.chain.handler.Handler;
import ru.daniil4jk.strongram.core.context.request.RequestContextImpl;
import ru.daniil4jk.strongram.core.response.sender.accumulating.AccumulatingSender;
import ru.daniil4jk.strongram.core.util.Lazy;

public abstract class ChainedBot extends BaseBot {
    private final Lazy<Handler> chain = new Lazy<>(this::createChain);

    public ChainedBot(String username) {
        super(username);
    }

    @Override
    public void accept(Update update, AccumulatingSender sender) {
        var ctx = new RequestContextImpl(this, update, sender);
        chain.initOrGet().accept(ctx);
    }

    private Handler createChain() {
        return getChain().get().build();
    }

    protected abstract ChainFactory getChain();
}