package ru.daniil4jk.strongram;

import lombok.Getter;
import okhttp3.OkHttpClient;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.command.CommandRegistry;
import ru.daniil4jk.strongram.command.CommandRegistryImpl;
import ru.daniil4jk.strongram.context.BotContextImpl;
import ru.daniil4jk.strongram.dialog.DialogRegistry;
import ru.daniil4jk.strongram.dialog.DialogRegistryImpl;
import ru.daniil4jk.strongram.handler.UpdateHandler;
import ru.daniil4jk.strongram.handler.defaults.AddDefaultKeyboardToResponseUpdateHandler;
import ru.daniil4jk.strongram.handler.defaults.CommandUpdateHandler;
import ru.daniil4jk.strongram.handler.defaults.DialogUpdateHandler;

@Getter
public class TelegramChainedBotWithDefaults extends TelegramChainedBot {
    private final DialogRegistry dialogRegistry = new DialogRegistryImpl();
    private final CommandRegistry commandRegistry = new CommandRegistryImpl();
    private final AddDefaultKeyboardToResponseUpdateHandler addKeyboardHandler = new AddDefaultKeyboardToResponseUpdateHandler();

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
    public void modifyChain(UpdateHandler.ChainBuilder builder) {
        super.modifyChain(builder);

        builder.beforeAll(addKeyboardHandler)
                .afterAll(new DialogUpdateHandler())
                .afterAll(new CommandUpdateHandler(true,
                        () -> getCredentials().getBotName()));
    }

    @Override
    public void modifyBotContext(BotContextImpl.BotContextImplBuilder builder) {
        super.modifyBotContext(builder);

        builder.objectByClass(DialogRegistry.class, dialogRegistry)
                .objectByClass(CommandRegistry.class, commandRegistry);
    }
}
