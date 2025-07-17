package ru.daniil4jk.strongram;

import lombok.Getter;
import okhttp3.OkHttpClient;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.command.CommandRegistry;
import ru.daniil4jk.strongram.command.CommandRegistryImpl;
import ru.daniil4jk.strongram.context.BotContextImpl;
import ru.daniil4jk.strongram.dialog.DialogRegistry;
import ru.daniil4jk.strongram.dialog.DialogRegistryImpl;
import ru.daniil4jk.strongram.handler.*;
import ru.daniil4jk.strongram.keyboard.ButtonWithCallbackRegistry;
import ru.daniil4jk.strongram.keyboard.ButtonWithCallbackRegistryImpl;

@Getter
public class TelegramChainedBotWithDefaults extends TelegramChainedBot {
    private DialogRegistry dialogRegistry;
    private CommandRegistry commandRegistry;
    private ButtonWithCallbackRegistry buttonWithCallbackRegistry;
    private AddDefaultKeyboardToResponseUpdateHandler addKeyboardHandler;

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

        addKeyboardHandler = new AddDefaultKeyboardToResponseUpdateHandler();

        builder.beforeAll(addKeyboardHandler)
                .afterAll(new DialogUpdateHandler())
                .afterAll(new CommandUpdateHandler(true,
                        () -> getCredentials().getBotName()))
                .afterAll(new KeyboardCallbackUpdateHandler());
    }

    @Override
    public void modifyBotContext(BotContextImpl.BotContextImplBuilder builder) {
        super.modifyBotContext(builder);

        dialogRegistry = new DialogRegistryImpl();
        commandRegistry = new CommandRegistryImpl();
        buttonWithCallbackRegistry = new ButtonWithCallbackRegistryImpl();

        builder.objectByClass(DialogRegistry.class, dialogRegistry)
                .objectByClass(CommandRegistry.class, commandRegistry)
                .objectByClass(ButtonWithCallbackRegistry.class, buttonWithCallbackRegistry);
    }
}
