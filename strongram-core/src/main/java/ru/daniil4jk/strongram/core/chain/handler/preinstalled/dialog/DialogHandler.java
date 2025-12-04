package ru.daniil4jk.strongram.core.chain.handler.preinstalled.dialog;

import org.jetbrains.annotations.NotNull;
import ru.daniil4jk.strongram.core.chain.context.RequestContext;
import ru.daniil4jk.strongram.core.chain.context.TelegramUUID;
import ru.daniil4jk.strongram.core.chain.handler.BaseHandler;
import ru.daniil4jk.strongram.core.dialog.Dialog;

import java.util.*;

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
        TelegramUUID uuid = ctx.getUserId();

        boolean foundSuitable = processDialogs(ctx, activeDialogs.get(uuid));
        if (!foundSuitable) {
            processNext(ctx);
        }

        Collection<Dialog> newDialogs = getNewDialogs(ctx);
        newDialogs.forEach(d -> sendFirstAsk(d, ctx));
        addNewDialogs(newDialogs, uuid);
    }

    public boolean processDialogs(@NotNull RequestContext ctx, @NotNull List<Dialog> dialogs) {
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
            } else {
                ctx.respond(dialog.repeatAsk());
            }
        }

        return foundSuitable;
    }

    private void sendFirstAsk(@NotNull Dialog dialog, @NotNull RequestContext ctx) {
        ctx.respond(dialog.firstAsk());
    }

    private void addNewDialogs(@NotNull Collection<Dialog> newDialogs, TelegramUUID uuid) {
        if (!newDialogs.isEmpty()) {
            if (newDialogs instanceof List<Dialog> list) {
                activeDialogs.addAll(uuid, list);
            } else {
                activeDialogs.addAll(uuid, new ArrayList<>(newDialogs));
            }
        }
    }

    private Collection<Dialog> getNewDialogs(@NotNull RequestContext ctx) {
        return ctx.getStorage().getCollection(DIALOGS_CONTEXT_FIELD_NAME);
    }
}
