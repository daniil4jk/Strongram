package ru.daniil4jk.strongram;

import org.jetbrains.annotations.NotNull;
import ru.daniil4jk.strongram.context.BotContextImpl;
import ru.daniil4jk.strongram.handler.UpdateHandler;

public interface ChainedBot {
    default UpdateHandler getRootHandlerInChain(@NotNull UpdateHandler.ChainBuilder basicChain) {
        return basicChain.build();
    }

    default void modifyBotContext(BotContextImpl.BotContextImplBuilder builder) {
        //do nothing
    }
}
