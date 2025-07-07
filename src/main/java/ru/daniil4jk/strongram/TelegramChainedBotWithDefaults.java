package ru.daniil4jk.strongram;

import lombok.Getter;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import ru.daniil4jk.strongram.command.CommandRegistry;
import ru.daniil4jk.strongram.command.CommandRegistryImpl;
import ru.daniil4jk.strongram.context.BotContext;
import ru.daniil4jk.strongram.context.BotContextImpl;
import ru.daniil4jk.strongram.handler.one_time.OneTimeHandlerRegistry;
import ru.daniil4jk.strongram.handler.one_time.OneTimeHandlerRegistryImpl;
import ru.daniil4jk.strongram.handler.permanent.CommandPermanentHandler;
import ru.daniil4jk.strongram.handler.permanent.KeyboardCallbackPermanentHandler;
import ru.daniil4jk.strongram.handler.permanent.OneTimeChainPermanentHandler;
import ru.daniil4jk.strongram.handler.permanent.PermanentHandler;
import ru.daniil4jk.strongram.keyboard.ButtonWithCallbackRegistry;
import ru.daniil4jk.strongram.keyboard.ButtonWithCallbackRegistryImpl;

@Getter
public class TelegramChainedBotWithDefaults extends TelegramChainedBot {
    private final OneTimeHandlerRegistry oneTimeHandlerRegistry =  new OneTimeHandlerRegistryImpl();
    private final CommandRegistry commandRegistry = new CommandRegistryImpl();
    private final ButtonWithCallbackRegistry buttonWithCallbackRegistry = new ButtonWithCallbackRegistryImpl();

    public TelegramChainedBotWithDefaults(BotCredentials credentials) {
        super(credentials);
    }

    public TelegramChainedBotWithDefaults(OkHttpClient httpClient, BotCredentials credentials) {
        super(httpClient, credentials);
    }

    @Override
    protected void extractBaseComponents(@NotNull PermanentHandler.ChainBuilder builder) {
        builder
                .afterAll(new OneTimeChainPermanentHandler())
                .afterAll(new CommandPermanentHandler(true, () -> getCredentials().getBotName()))
                .afterAll(new KeyboardCallbackPermanentHandler());
    }

    @Override
    public void modifyBotContext(BotContextImpl.BotContextImplBuilder builder) {
        super.modifyBotContext(builder);
        builder.objectByClass(OneTimeHandlerRegistry.class, oneTimeHandlerRegistry);
        builder.objectByClass(CommandRegistry.class, commandRegistry);
        builder.objectByClass(ButtonWithCallbackRegistry.class, buttonWithCallbackRegistry);
    }
}
