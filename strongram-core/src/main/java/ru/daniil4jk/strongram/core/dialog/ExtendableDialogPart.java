package ru.daniil4jk.strongram.core.dialog;

import lombok.AccessLevel;
import lombok.Getter;
import ru.daniil4jk.strongram.core.chain.context.RequestContext;
import ru.daniil4jk.strongram.core.chain.filter.Filter;
import ru.daniil4jk.strongram.core.dialog.state.DialogContext;

public abstract class ExtendableDialogPart<ENUM extends Enum<ENUM>> implements DialogPart<ENUM> {
    protected abstract Filter getFilter();

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
}
