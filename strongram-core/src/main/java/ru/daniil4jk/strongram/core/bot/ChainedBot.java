package ru.daniil4jk.strongram.core.bot;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.core.chain.context.ManagedRequestContext;
import ru.daniil4jk.strongram.core.chain.context.RequestContextImpl;
import ru.daniil4jk.strongram.core.chain.factory.ChainFactory;
import ru.daniil4jk.strongram.core.chain.handler.Handler;
import ru.daniil4jk.strongram.core.util.Lazy;

import java.util.List;

public abstract class ChainedBot extends BaseBot {
    private final Lazy<Handler> chain = new Lazy<>(this::createChain);
    private final ChainFactory creator;

    public ChainedBot(String username, ChainFactory creator) {
        super(username);
        this.creator = creator;
    }

    public ChainedBot(TelegramClient telegramClient, String username, ChainFactory creator) {
        super(telegramClient, username);
        this.creator = creator;
    }

    @Override
    public final List<BotApiMethod<?>> apply(Update update) {
        var ctx = new ManagedRequestContext(new RequestContextImpl(this, update));
        chain.initOrGet().accept(ctx);
        ctx.deactivate();
        return ctx.getResponses();
    }

    private Handler createChain() {
        return creator.get().build();
    }
}
