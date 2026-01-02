package ru.daniil4jk.strongram.core.chain.handler.preinstalled;

import org.jetbrains.annotations.NotNull;
import ru.daniil4jk.strongram.core.chain.handler.BaseHandler;
import ru.daniil4jk.strongram.core.context.request.RequestContext;
import ru.daniil4jk.strongram.core.context.request.TelegramUUID;
import ru.daniil4jk.strongram.core.dialog.Dialog;
import ru.daniil4jk.strongram.core.dialog.DialogRepository;
import ru.daniil4jk.strongram.core.dialog.InMemoryDialogRepository;

import java.util.ArrayList;
import java.util.List;

public class DialogHandler extends BaseHandler {
    public static final String DIALOGS_CONTEXT_FIELD_NAME = "ru.daniil4jk.strongram_dialogs";
    private final DialogRepository activeDialogs;

    public DialogHandler() {
        this(new InMemoryDialogRepository());
    }

    public DialogHandler(DialogRepository activeDialogs) {
        this.activeDialogs = activeDialogs;
    }

    @Override
    protected final void process(@NotNull RequestContext ctx) {
        boolean foundSuitable = tryProcessExistingDialogs(ctx);
        if (foundSuitable) return;

        processNext(ctx);

        registerNewDialogs(ctx);
    }

    public boolean tryProcessExistingDialogs(@NotNull RequestContext ctx) {
        List<Dialog> dialogs = activeDialogs.get(ctx.getUUID());
        if (dialogs == null || dialogs.isEmpty()) {
            return false;
        }

        TelegramUUID uuid = ctx.getUUID();
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
        List<Dialog> dialogs = getNewDialogs(ctx);
        if (!dialogs.isEmpty()) {
            dialogs.forEach(d -> d.sendNotification(ctx));
            activeDialogs.addAll(ctx.getUUID(), dialogs);
        }
    }

    private @NotNull List<Dialog> getNewDialogs(@NotNull RequestContext ctx) {
        ArrayList<Dialog> dialogs = getDialogsFromContext(ctx);
        dialogs.removeIf(Dialog::isStopped);
        return dialogs;
    }

    private @NotNull ArrayList<Dialog> getDialogsFromContext(@NotNull RequestContext ctx) {
        return new ArrayList<>(ctx.getRequestScopeStorage().getCollection(DIALOGS_CONTEXT_FIELD_NAME));
    }
}
