package ru.daniil4jk.strongram.core.dialog;

import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import ru.daniil4jk.strongram.core.chain.context.RequestContext;
import ru.daniil4jk.strongram.core.dialog.part.DialogPart;
import ru.daniil4jk.strongram.core.dialog.state.DialogContext;
import ru.daniil4jk.strongram.core.dialog.state.DialogContextImpl;

import java.util.EnumMap;
import java.util.Map;

public class DialogImpl<ENUM extends Enum<ENUM>> implements Dialog {
    private final Map<ENUM, DialogPart<ENUM>> parts;
    @Getter(AccessLevel.PROTECTED)
    private final DialogContext<ENUM> dialogContext;

    public DialogImpl(@NotNull ENUM initState, Map<ENUM, DialogPart<ENUM>> parts) {
        this(initState);
        parts.values().forEach(p -> p.injectDialogContext(dialogContext));
        this.parts.putAll(parts);
    }

    public DialogImpl(@NotNull ENUM initState) {
        this.parts = new EnumMap<>(initState.getDeclaringClass());
        this.dialogContext = new DialogContextImpl<>(initState);
    }

    public void add(ENUM state, DialogPart<ENUM> part) {
        check();
        part.injectDialogContext(dialogContext);
        parts.put(state, part);
    }

    @Override
    public void sendNotification(RequestContext ctx) {
        check();
        getCurrentPart().sendNotification(ctx);
    }

    @Override
    public synchronized void accept(RequestContext ctx) {
        check();
        getCurrentPart().accept(ctx);
    }

    @Override
    public boolean isStopped() {
        return dialogContext.isStopped();
    }

    private void check() {
        if (isStopped()) {
            throw new IllegalStateException("dialog is stopped!");
        }
    }

    @Override
    public boolean canAccept(RequestContext ctx) {
        return getCurrentPart().canAccept(ctx);
    }

    private DialogPart<ENUM> getCurrentPart() {
        return parts.get(dialogContext.getState());
    }

    private final Object lock = new Object();
    @Override
    public Object getLock() {
        check();
        return lock;
    }
}