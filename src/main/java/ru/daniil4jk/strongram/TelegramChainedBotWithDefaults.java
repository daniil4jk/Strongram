package ru.daniil4jk.strongram;

import lombok.Getter;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.generics.TelegramClient;
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
    private final DialogRegistry dialogRegistry = getBotContext().getByClass(DialogRegistry.class);
    private final CommandRegistry commandRegistry = getBotContext().getByClass(CommandRegistry.class);
    private final ButtonWithCallbackRegistry buttonWithCallbackRegistry =
            getBotContext().getByClass(ButtonWithCallbackRegistry.class);

    public TelegramChainedBotWithDefaults(BotCredentials credentials) {
        super(credentials);
    }

    public TelegramChainedBotWithDefaults(OkHttpClient httpClient, BotCredentials credentials) {
        super(httpClient, credentials);
    }

    public TelegramChainedBotWithDefaults(TelegramClient telegramClient, BotCredentials credentials) {
        super(telegramClient, credentials);
    }

    @Override
    public void updateChain(@NotNull UpdateHandler.ChainBuilder builder) {
        super.updateChain(builder);
        builder.afterAll(new DialogUpdateHandler())
                .afterAll(new CommandUpdateHandler(true,
                        () -> getCredentials().getBotName()))
                .afterAll(new KeyboardCallbackUpdateHandler());
    }

    @Override
    public void modifyBotContext(BotContextImpl.BotContextImplBuilder builder) {
        super.modifyBotContext(builder);
        builder.objectByClass(DialogRegistry.class, new DialogRegistryImpl())
                .objectByClass(CommandRegistry.class, new CommandRegistryImpl())
                .objectByClass(ButtonWithCallbackRegistry.class, new ButtonWithCallbackRegistryImpl());
    }
}
