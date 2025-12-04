package ru.daniil4jk.strongram.core.dialog;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import ru.daniil4jk.strongram.core.chain.context.RequestContext;
import ru.daniil4jk.strongram.core.dialog.state.DialogContext;

import java.util.function.BiConsumer;

public record DialogPartImpl<ENUM extends Enum<ENUM>> (
        BotApiMethod<?> ask,
        BiConsumer<RequestContext, DialogContext<ENUM>> handler
) implements DialogPart<ENUM> {
    public DialogPartImpl(
            Class<ENUM> enumClass,
            BotApiMethod<?> ask,
            BiConsumer<RequestContext, DialogContext<ENUM>> handler
    ) {
        this(ask, handler);
    }

    @Override
    public BotApiMethod<?> ask() {
        return ask;
    }

    @Override
    public void accept(RequestContext requestContext, DialogContext<ENUM> dialogContext) {
        handler.accept(requestContext, dialogContext);
    }
}
