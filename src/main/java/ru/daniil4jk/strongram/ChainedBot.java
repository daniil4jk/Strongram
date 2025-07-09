package ru.daniil4jk.strongram;

import org.jetbrains.annotations.NotNull;
import ru.daniil4jk.strongram.context.BotContextImpl;
import ru.daniil4jk.strongram.handler.UpdateHandler;

public interface ChainedBot {
    default void updateChain(@NotNull UpdateHandler.ChainBuilder builder) {
        //do nothing
    }

    default void modifyBotContext(BotContextImpl.BotContextImplBuilder builder) {
        //do nothing
    }
}
