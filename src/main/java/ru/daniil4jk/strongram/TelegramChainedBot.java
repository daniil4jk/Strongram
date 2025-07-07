package ru.daniil4jk.strongram;

import okhttp3.OkHttpClient;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.context.*;
import ru.daniil4jk.strongram.handler.permanent.PermanentHandler;

public abstract class TelegramChainedBot extends TelegramBot implements ChainedBot {
    private final PermanentHandler chain; {
        var builder = PermanentHandler.chainBuilder();
        extractBaseComponents(builder);
        chain = getRootHandlerInChain(builder);
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

    @Override
    public BotApiMethod<?> process(Update update) {
        return chain.process(update, botContext);
    }

    protected abstract void extractBaseComponents(PermanentHandler.ChainBuilder builder);

    @Override
    public void modifyBotContext(BotContextImpl.BotContextImplBuilder builder) {
        ChainedBot.super.modifyBotContext(builder);
        builder.objectByClass(TelegramClient.class, getClient());
    }
}
