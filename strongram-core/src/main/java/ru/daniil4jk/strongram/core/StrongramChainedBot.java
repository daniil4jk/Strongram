package ru.daniil4jk.strongram.core;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.core.context.BotContext;
import ru.daniil4jk.strongram.core.context.BotContextImpl;
import ru.daniil4jk.strongram.core.handler.UpdateHandler;

@Slf4j
public abstract class StrongramChainedBot extends StrongramBot implements ChainedBot {
    private volatile UpdateHandler chain;
    private volatile BotContext botContext;

    public StrongramChainedBot(TelegramClient telegramClient, BotCredentials credentials) {
        super(telegramClient, credentials);
    }

    public UpdateHandler getChain() {
        if (chain == null) {
            synchronized (this) {
                if (chain == null) {
                    return this.chain = createChain();
                }
            }
        }
        return chain;
    }

    private UpdateHandler createChain() {
        var builder = UpdateHandler.chainBuilder();
        modifyChain(builder);
        UpdateHandler root = builder.build();
        if (root == null) {
            log.warn("chain is empty! You can add some links to chain overriding modifyChain()");
        }
        return root;
    }

    public BotContext getBotContext() {
        if (botContext == null) {
            synchronized (this) {
                if (botContext == null) {
                    return this.botContext = createContext();
                }
            }
        }
        return botContext;
    }

    private BotContext createContext() {
        var builder = BotContextImpl.builder();
        modifyBotContext(builder);
        return builder.build();
    }

    @Override
    public BotApiMethod<?> process(Update update) {
        if (log.isDebugEnabled()) {
            log.debug("Update {} pushed to chain", update.getUpdateId());
        }
        return getChain().process(update, getBotContext());
    }

    @Override
    public void modifyBotContext(BotContextImpl.BotContextImplBuilder builder) {
        ChainedBot.super.modifyBotContext(builder);
        builder.objectByClass(TelegramClient.class, getClient());
    }
}
