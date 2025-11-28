package ru.daniil4jk.strongram.core;

import ru.daniil4jk.strongram.core.context.BotContextImpl;
import ru.daniil4jk.strongram.core.handler.UpdateHandler;

public interface Chained {
    default void modifyChain(UpdateHandler.ChainBuilder builder) {
        //do nothing
    }

    default void modifyBotContext(BotContextImpl.BotContextImplBuilder builder) {
        //do nothing
    }
}
