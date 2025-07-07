package ru.daniil4jk.strongram;

import lombok.Getter;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import ru.daniil4jk.strongram.command.CommandRegistry;
import ru.daniil4jk.strongram.command.CommandRegistryImpl;
import ru.daniil4jk.strongram.context.BotContextImpl;
import ru.daniil4jk.strongram.dialog.DialogRegistry;
import ru.daniil4jk.strongram.dialog.DialogRegistryImpl;
import ru.daniil4jk.strongram.handler.CommandUpdateHandler;
import ru.daniil4jk.strongram.handler.DialogUpdateHandler;
import ru.daniil4jk.strongram.handler.KeyboardCallbackUpdateHandler;
import ru.daniil4jk.strongram.handler.UpdateHandler;
import ru.daniil4jk.strongram.keyboard.ButtonWithCallbackRegistry;
import ru.daniil4jk.strongram.keyboard.ButtonWithCallbackRegistryImpl;

@Getter
public class TelegramChainedBotWithDefaults extends TelegramChainedBot {
    private final DialogRegistry dialogRegistry = new DialogRegistryImpl();
    private final CommandRegistry commandRegistry = new CommandRegistryImpl();
    private final ButtonWithCallbackRegistry buttonWithCallbackRegistry = new ButtonWithCallbackRegistryImpl();

    public TelegramChainedBotWithDefaults(BotCredentials credentials) {
        super(credentials);
    }

    public TelegramChainedBotWithDefaults(OkHttpClient httpClient, BotCredentials credentials) {
        super(httpClient, credentials);
    }

    @Override
    protected void extractBaseComponents(@NotNull UpdateHandler.ChainBuilder builder) {
        builder
                .afterAll(new DialogUpdateHandler())
                .afterAll(new CommandUpdateHandler(true, () -> getCredentials().getBotName()))
                .afterAll(new KeyboardCallbackUpdateHandler());
    }

    @Override
    public void modifyBotContext(BotContextImpl.BotContextImplBuilder builder) {
        super.modifyBotContext(builder);
        builder.objectByClass(DialogRegistry.class, dialogRegistry);
        builder.objectByClass(CommandRegistry.class, commandRegistry);
        builder.objectByClass(ButtonWithCallbackRegistry.class, buttonWithCallbackRegistry);
    }
}
