package ru.daniil4jk.strongram.core.chain.handler.preinstalled;

import org.jetbrains.annotations.NotNull;
import ru.daniil4jk.strongram.core.chain.context.RequestContext;
import ru.daniil4jk.strongram.core.chain.context.TelegramUUID;
import ru.daniil4jk.strongram.core.chain.handler.BaseHandler;
import ru.daniil4jk.strongram.core.dialog.Dialog;

import java.util.*;

public final class DialogHandler extends BaseHandler {
    public static final String DIALOGS_CONTEXT_FIELD_NAME = "ru.daniil4jk.strongram_dialogs";
    private final Map<TelegramUUID, List<Dialog>> activeDialogs = new HashMap<>();

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

    public boolean processDialogs(RequestContext ctx, List<Dialog> dialogs) {
        TelegramUUID uuid = ctx.getUserId();
        boolean foundSuitable = false;

        for (Dialog dialog : dialogs) {
            if (dialog.canAccept(ctx)) {
                synchronized (dialog.getLock()) {
                    dialog.accept(ctx);
                    if (dialog.isStopped()) {
                        removeDialog(uuid, dialog);
                    }
                }
                foundSuitable = true;
            } else {
                ctx.respond(dialog.repeatAsk());
            }
        }

        return foundSuitable;
    }

    private void removeDialog(TelegramUUID uuid, Dialog dialog) {
        activeDialogs.compute(uuid,
                (u, dialogs) -> {
                    if (dialogs == null) {
                        throw new IllegalStateException("попытка удалить диалог у пользователя с несуществующим списком диалогов");
                    }
                    dialogs.remove(dialog);
                    if (dialogs.isEmpty()) {
                        return null;
                    }
                    return dialogs;
                }
        );
    }

    private void sendFirstAsk(Dialog dialog, RequestContext ctx) {
        ctx.respond(dialog.firstAsk());
    }

    private void addNewDialogs(Collection<Dialog> newDialogs, TelegramUUID uuid) {
        if (!newDialogs.isEmpty()) {
            activeDialogs.computeIfAbsent(uuid, u -> new ArrayList<>())
                    .addAll(newDialogs);
        }
    }

    private Collection<Dialog> getNewDialogs(RequestContext ctx) {
        return ctx.getStorage().getCollection(DIALOGS_CONTEXT_FIELD_NAME);
    }
}
