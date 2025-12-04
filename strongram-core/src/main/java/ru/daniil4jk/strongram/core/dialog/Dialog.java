package ru.daniil4jk.strongram.core.dialog;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import ru.daniil4jk.strongram.core.chain.context.RequestContext;

import java.util.function.Consumer;

public interface Dialog extends Consumer<RequestContext> {
    BotApiMethod<?> firstAsk();
    BotApiMethod<?> repeatAsk();
    boolean isStopped();
    boolean canAccept(RequestContext ctx);
}
