package ru.daniil4jk.strongram.core.bot;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.core.chain.factory.ChainFactory;
import ru.daniil4jk.strongram.core.chain.handler.Handler;
import ru.daniil4jk.strongram.core.context.request.ManagedRequestContext;
import ru.daniil4jk.strongram.core.context.request.RequestContextImpl;
import ru.daniil4jk.strongram.core.util.Lazy;

import java.util.List;

public abstract class ChainedBot extends BaseBot {
    private final Lazy<Handler> chain = new Lazy<>(this::createChain);

    public ChainedBot(String username) {
        super(username);
    }

    public ChainedBot(TelegramClient telegramClient, String username) {
        super(telegramClient, username);
    }

    @Override
    public final List<BotApiMethod<?>> apply(Update update) {
        var ctx = new ManagedRequestContext(new RequestContextImpl(this, update));
        chain.initOrGet().accept(ctx);
        ctx.deactivate();
        return ctx.getResponses();
    }

    private Handler createChain() {
        return getChain().get().build();
    }

    protected abstract ChainFactory getChain();
}
