package ru.daniil4jk.strongram.core.chain.handler.preinstalled;

import org.jetbrains.annotations.NotNull;
import ru.daniil4jk.strongram.core.chain.context.RequestContext;
import ru.daniil4jk.strongram.core.chain.context.TelegramUUID;
import ru.daniil4jk.strongram.core.chain.handler.BaseHandler;
import ru.daniil4jk.strongram.core.dialog.Dialog;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class DialogHandler extends BaseHandler {
    public static final String DIALOGS_CONTEXT_FIELD_NAME = "ru.daniil4jk.strongram_dialogs";
    private final Map<TelegramUUID, Dialog> activeDialogs = new HashMap<>();

    @Override
    protected void process(@NotNull RequestContext ctx) {
        TelegramUUID uuid = ctx.getUserId();
        if (activeDialogs.containsKey(uuid)) {
            var dialog = activeDialogs.get(uuid);
            synchronized (dialog) {
                dialog.accept(ctx);
                if (dialog.isStopped()) {
                    activeDialogs.remove(uuid, dialog);
                }
            }
        }
        super.process(ctx);
        addNewDialogs(ctx);
    }

    private void addNewDialogs(@NotNull RequestContext ctx) {
        Collection<Dialog> dialogs = ctx.getStorage().getCollection(DIALOGS_CONTEXT_FIELD_NAME);
        if (!dialogs.isEmpty()) {
            TelegramUUID uuid = ctx.getUserId();
            dialogs.forEach(dialog -> activeDialogs.put(uuid, dialog));
        }
    }
}
