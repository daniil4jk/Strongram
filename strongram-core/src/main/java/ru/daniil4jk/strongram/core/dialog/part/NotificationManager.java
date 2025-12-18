package ru.daniil4jk.strongram.core.dialog.part;

import ru.daniil4jk.strongram.core.context.request.RequestContext;
import ru.daniil4jk.strongram.core.context.storage.Storage;

import java.util.function.BiConsumer;

public class NotificationManager<ENUM extends Enum<ENUM>> {
    private final BiConsumer<RequestContext, Storage> firstNotification;
    private final BiConsumer<RequestContext, Storage> repeatNotification;
    private boolean firstNotificationSent;

    public NotificationManager(BiConsumer<RequestContext, Storage> firstNotification,
                               BiConsumer<RequestContext, Storage> repeatNotification) {
        this.firstNotification = firstNotification;
        this.repeatNotification = repeatNotification;
    }

    public void sendNotification(RequestContext rCtx, Storage dCtx) {
        if (firstNotificationSent) {
            repeatNotification.accept(rCtx, dCtx);
        } else {
            firstNotification.accept(rCtx, dCtx);
            firstNotificationSent = true;
        }
    }
}
