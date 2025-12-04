package ru.daniil4jk.strongram.core.dialog;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import ru.daniil4jk.strongram.core.chain.context.RequestContext;
import ru.daniil4jk.strongram.core.dialog.state.DialogContext;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface DialogPart<ENUM extends Enum<ENUM>> extends Consumer<RequestContext> {
    BotApiMethod<?> firstAsk();
    BotApiMethod<?> repeatAsk();
    boolean canAccept(RequestContext ctx);
    void injectDialogContext(DialogContext<ENUM> dCtx);
}
