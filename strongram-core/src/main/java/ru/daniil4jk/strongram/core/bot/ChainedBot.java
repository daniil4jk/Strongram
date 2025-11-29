package ru.daniil4jk.strongram.core.bot;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.core.chain.Chain;
import ru.daniil4jk.strongram.core.chain.context.ContextImpl;
import ru.daniil4jk.strongram.core.chain.handler.Handler;
import ru.daniil4jk.strongram.core.util.Lazy;

import java.util.List;

public abstract class ChainedBot extends BaseBot {
    private final Lazy<Handler> chain = new Lazy<>(this::createChain);

    public ChainedBot(BotCredentials credentials) {
        super(credentials);
    }

    public ChainedBot(TelegramClient telegramClient, BotCredentials credentials) {
        super(telegramClient, credentials);
    }

    @Override
    public final List<BotApiMethod<?>> apply(Update update) {
        var ctx = new ContextImpl(this, update);
        chain.initOrGet().accept(ctx);
        return ctx.getResponses();
    }

    private Handler createChain() {
        var chain = new Chain();
        configureChain(chain);
        return chain.build();
    }

    protected abstract void configureChain(Chain chain);
}
