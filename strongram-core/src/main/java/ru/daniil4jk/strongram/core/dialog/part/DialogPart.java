package ru.daniil4jk.strongram.core.dialog.part;

import ru.daniil4jk.strongram.core.context.request.RequestContext;
import ru.daniil4jk.strongram.core.context.dialog.DialogContext;

import java.util.function.Consumer;

public interface DialogPart<ENUM extends Enum<ENUM>> extends Consumer<RequestContext> {
    void sendNotification(RequestContext ctx);
    boolean canAccept(RequestContext ctx);
    void injectDialogContext(DialogContext<ENUM> dCtx);
}
