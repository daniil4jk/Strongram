package ru.daniil4jk.strongram.core.dialog.part;

import lombok.AccessLevel;
import lombok.Getter;
import ru.daniil4jk.strongram.core.chain.context.RequestContext;
import ru.daniil4jk.strongram.core.chain.filter.Filter;
import ru.daniil4jk.strongram.core.dialog.state.DialogContext;

public abstract class ExtendableDialogPart<ENUM extends Enum<ENUM>> implements DialogPart<ENUM> {
    private final NotificationManager<ENUM> notificationManager = new NotificationManager<>(
            this::firstNotification, this::repeatNotification
    );

    protected abstract Filter getFilter();
    protected abstract void firstNotification(RequestContext ctx);
    protected abstract void repeatNotification(RequestContext rCtx, DialogContext<ENUM> dCtx);

    @Getter(AccessLevel.PROTECTED)
    private DialogContext<ENUM> dCtx;

    @Override
    public boolean canAccept(RequestContext ctx) {
        return getFilter().test(ctx);
    }

    @Override
    public void injectDialogContext(DialogContext<ENUM> dCtx) {
        this.dCtx = dCtx;
    }

    @Override
    public void sendNotification(RequestContext ctx) {
        notificationManager.sendNotification(ctx, dCtx);
    }
}
