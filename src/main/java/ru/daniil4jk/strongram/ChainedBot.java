package ru.daniil4jk.strongram;

import org.jetbrains.annotations.NotNull;
import ru.daniil4jk.strongram.context.BotContextImpl;
import ru.daniil4jk.strongram.handler.UpdateHandler;

public interface ChainedBot {
    default void updateChain(@NotNull UpdateHandler.ChainBuilder basicChain) {

    }

    default void modifyBotContext(BotContextImpl.BotContextImplBuilder builder) {
        //do nothing
    }
}
