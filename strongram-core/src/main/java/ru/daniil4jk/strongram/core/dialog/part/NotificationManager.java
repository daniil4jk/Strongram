package ru.daniil4jk.strongram.core.dialog.part;

import ru.daniil4jk.strongram.core.context.dialog.DialogContext;
import ru.daniil4jk.strongram.core.context.request.RequestContext;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class NotificationManager<ENUM extends Enum<ENUM>> {
    private final Consumer<RequestContext> firstNotification;
    private final BiConsumer<RequestContext, DialogContext<ENUM>> repeatNotification;
    private boolean firstNotificationSent;

    public NotificationManager(Consumer<RequestContext> firstNotification,
                               BiConsumer<RequestContext, DialogContext<ENUM>> repeatNotification) {
        this.firstNotification = firstNotification;
        this.repeatNotification = repeatNotification;
    }

    public void sendNotification(RequestContext rCtx, DialogContext<ENUM> dCtx) {
        if (firstNotificationSent) {
            repeatNotification.accept(rCtx, dCtx);
        } else {
            firstNotification.accept(rCtx);
            firstNotificationSent = true;
        }
    }
}
