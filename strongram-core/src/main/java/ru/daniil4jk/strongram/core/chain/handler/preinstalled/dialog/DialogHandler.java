package ru.daniil4jk.strongram.core.chain.handler.preinstalled.dialog;

import org.jetbrains.annotations.NotNull;
import ru.daniil4jk.strongram.core.chain.context.RequestContext;
import ru.daniil4jk.strongram.core.chain.context.TelegramUUID;
import ru.daniil4jk.strongram.core.chain.handler.BaseHandler;
import ru.daniil4jk.strongram.core.dialog.Dialog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class DialogHandler extends BaseHandler {
    public static final String DIALOGS_CONTEXT_FIELD_NAME = "ru.daniil4jk.strongram_dialogs";
    private final DialogStorage activeDialogs;

    public DialogHandler() {
        this(new MemoryDialogStorage());
    }

    public DialogHandler(DialogStorage activeDialogs) {
        this.activeDialogs = activeDialogs;
    }

    @Override
    protected void process(@NotNull RequestContext ctx) {
        boolean foundSuitable = tryProcessExistingDialogs(ctx);
        if (foundSuitable) return;

        processNext(ctx);

        registerNewDialogs(ctx);
    }

    public boolean tryProcessExistingDialogs(@NotNull RequestContext ctx) {
        List<Dialog> dialogs = activeDialogs.get(ctx.getUserId());
        if (dialogs == null || dialogs.isEmpty()) {
            return false;
        }

        TelegramUUID uuid = ctx.getUserId();
        boolean foundSuitable = false;

        for (Dialog dialog : dialogs) {
            if (dialog.canAccept(ctx)) {
                synchronized (dialog.getLock()) {
                    dialog.accept(ctx);
                    if (dialog.isStopped()) {
                        activeDialogs.remove(uuid, dialog);
                    }
                }
                foundSuitable = true;
                break;
            }
        }

        dialogs.forEach(d -> d.sendNotification(ctx));

        return foundSuitable;
    }

    private void registerNewDialogs(RequestContext ctx) {
        Collection<Dialog> dialogs = getNewDialogs(ctx);
        if (!dialogs.isEmpty()) {
            dialogs.forEach(d -> d.sendNotification(ctx));
            addNewDialogs(dialogs, ctx.getUserId());
        }
    }

    private void addNewDialogs(@NotNull Collection<Dialog> newDialogs, TelegramUUID uuid) {
        if (newDialogs instanceof ArrayList<Dialog> list) {
            activeDialogs.addAll(uuid, list);
        } else {
            activeDialogs.addAll(uuid, new ArrayList<>(newDialogs));
        }
    }

    private @NotNull Collection<Dialog> getNewDialogs(@NotNull RequestContext ctx) {
        Collection<Dialog> dialogs = ctx.getStorage().getCollection(DIALOGS_CONTEXT_FIELD_NAME);
        dialogs.removeIf(Dialog::isStopped);
        return dialogs;
    }
}
