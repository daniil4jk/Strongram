package ru.daniil4jk.strongram;

import org.jetbrains.annotations.NotNull;
import ru.daniil4jk.strongram.context.BotContext;
import ru.daniil4jk.strongram.context.BotContextImpl;
import ru.daniil4jk.strongram.handler.permanent.PermanentHandler;

public interface ChainedBot {
    default PermanentHandler getRootHandlerInChain(@NotNull PermanentHandler.ChainBuilder basicChain) {
        return basicChain.build();
    }

    default void modifyBotContext(BotContextImpl.BotContextImplBuilder builder) {
        //do nothing
    }
}
