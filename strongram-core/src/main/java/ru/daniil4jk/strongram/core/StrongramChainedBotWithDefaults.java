package ru.daniil4jk.strongram.core;

import lombok.Getter;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.core.command.CommandRegistry;
import ru.daniil4jk.strongram.core.command.CommandRegistryImpl;
import ru.daniil4jk.strongram.core.context.BotContextImpl;
import ru.daniil4jk.strongram.core.dialog.DialogRegistry;
import ru.daniil4jk.strongram.core.dialog.DialogRegistryImpl;
import ru.daniil4jk.strongram.core.handler.UpdateHandler;
import ru.daniil4jk.strongram.core.handler.defaults.AddDefaultKeyboardToResponseUpdateHandler;
import ru.daniil4jk.strongram.core.handler.defaults.CommandUpdateHandler;
import ru.daniil4jk.strongram.core.handler.defaults.DialogUpdateHandler;

@Getter
public class StrongramChainedBotWithDefaults extends StrongramChainedBot {
    private final DialogRegistry dialogRegistry = new DialogRegistryImpl();
    private final CommandRegistry commandRegistry = new CommandRegistryImpl();
    private final AddDefaultKeyboardToResponseUpdateHandler addKeyboardHandler = new AddDefaultKeyboardToResponseUpdateHandler();

    public StrongramChainedBotWithDefaults(TelegramClient telegramClient, BotCredentials credentials) {
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
