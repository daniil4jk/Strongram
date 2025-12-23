package ru.daniil4jk.strongram.core.bot;

import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.core.chain.factory.ChainFactory;
import ru.daniil4jk.strongram.core.chain.handler.Handler;
import ru.daniil4jk.strongram.core.context.request.ManagedRequestContext;
import ru.daniil4jk.strongram.core.context.request.RequestContextImpl;
import ru.daniil4jk.strongram.core.util.Lazy;

import java.util.List;

/**
 * An abstract base class for Telegram bots that utilize a chain-of-responsibility pattern
 * to process incoming updates.
 *
 * <p>This class extends {@link BaseBot} and provides infrastructure for building and managing
 * a chain of handlers via a {@link ChainFactory}. The handler chain is lazily initialized
 * upon the first update received, ensuring efficient startup and resource usage.</p>
 *
 * <p>Subclasses must implement the {@link #getChain()} method to provide a {@link ChainFactory}
 * that constructs the handler chain used to process updates. Each incoming {@link Update}
 * is wrapped in a {@link ManagedRequestContext}, passed through the chain, and any responses
 * generated during processing are collected and returned.</p>
 *
 * @see BaseBot
 * @see ChainFactory
 * @see Handler
 * @see ManagedRequestContext
 */
public abstract class ChainedBot extends BaseBot {
    private final Lazy<Handler> chain = new Lazy<>(this::createChain);

    /**
     * Constructs a new {@code ChainedBot} with the specified username.
     *
     * @param username the bot's username; must not be null
     */
    public ChainedBot(String username) {
        super(username);
    }

    /**
     * Constructs a new {@code ChainedBot} with a custom Telegram client and username.
     *
     * @param telegramClient the Telegram client to use for API calls; must not be null
     * @param username the bot's username; must not be null
     */
    public ChainedBot(TelegramClient telegramClient, String username) {
        super(telegramClient, username);
    }

    /**
     * Processes an incoming update by passing it through the handler chain.
     *
     * <p>The method creates a new {@link ManagedRequestContext} for the update
     * and exchange request to responses using the handler chain.
     * After processing, the context is deactivated to prevent further modifications.</p>
     *
     * @param update the incoming Telegram update; must not be null
     * @return a list of response methods (e.g., messages, edits) to be sent back to Telegram;
     *         never null, but may be empty if no responses were generated
     */
    @Override
    public final List<PartialBotApiMethod<?>> apply(Update update) {
        var ctx = new ManagedRequestContext(new RequestContextImpl(this, update));
        chain.initOrGet().accept(ctx);
        ctx.deactivate();
        return ctx.getResponses();
    }

    /**
     * Creates and initializes the handler chain using the factory provided by {@link #getChain()}.
     *
     * <p>This method is called only once by the {@code Lazy} wrapper when the chain is first needed.
     * Subsequent calls return the already-built handler chain.</p>
     *
     * @return the constructed handler chain; never null
     */
    private Handler createChain() {
        return getChain().get().build();
    }

    /**
     * Provides the chain factory responsible for building the handler chain.
     *
     * <p>Subclasses must implement this method to return a non-null {@link ChainFactory}
     * that defines how the handler chain should be constructed. The returned factory
     * is used in {@link #createChain()} to build the actual chain of handlers.</p>
     *
     * @return a {@link ChainFactory} used to build the handler chain; must not return null
     */
    protected abstract ChainFactory getChain();
}