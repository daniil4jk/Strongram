package ru.daniil4jk.strongram;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.context.BotContext;
import ru.daniil4jk.strongram.context.BotContextImpl;
import ru.daniil4jk.strongram.handler.UpdateHandler;

@Slf4j
public abstract class TelegramChainedBot extends TelegramBot implements ChainedBot {
    private final UpdateHandler chain; {
        var builder = UpdateHandler.chainBuilder();
        updateChain(builder);
        chain = builder.build();
        if (chain == null) {
            throw new IllegalStateException("Chain can`t be null! You can return basicChain.build() in defaults");
        }
    }
    private final BotContext botContext; {
        var builder = BotContextImpl.builder();
        modifyBotContext(builder);
        botContext = builder.build();
    }

    public TelegramChainedBot(BotCredentials credentials) {
        super(credentials);
    }

    public TelegramChainedBot(OkHttpClient httpClient, BotCredentials credentials) {
        super(httpClient, credentials);
    }

    public TelegramChainedBot(TelegramClient telegramClient, BotCredentials credentials) {
        super(telegramClient, credentials);
    }

    @Override
    public BotApiMethod<?> process(Update update) {
        if (log.isDebugEnabled()) {
            log.debug("update processing by chain");
        }
        return chain.process(update, botContext);
    }

    @Override
    public void modifyBotContext(BotContextImpl.BotContextImplBuilder builder) {
        ChainedBot.super.modifyBotContext(builder);
        builder.objectByClass(TelegramClient.class, getClient());
    }
}
