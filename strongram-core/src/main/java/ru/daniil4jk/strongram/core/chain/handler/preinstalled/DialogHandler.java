package ru.daniil4jk.strongram.core.chain.handler.preinstalled;

import ru.daniil4jk.strongram.core.chain.context.RequestContext;
import ru.daniil4jk.strongram.core.chain.filter.Filter;
import ru.daniil4jk.strongram.core.chain.handler.BaseHandler;
import ru.daniil4jk.strongram.core.chain.context.TelegramUUID;
import ru.daniil4jk.strongram.core.dialog.Dialog;

import java.util.*;

public class DialogHandler extends BaseHandler {
    public static final String DIALOGS_CONTEXT_FIELD_NAME = "ru.daniil4jk.strongram_dialogs";
    private final Map<TelegramUUID, Dialog> activeDialogs = new HashMap<>();

    @Override
    protected Filter getFilter() {
        return ctx -> activeDialogs.containsKey(ctx.getUserId());
    }

    @Override
    protected void process(RequestContext ctx) {
        super.process(ctx);
        addNewDialogs(ctx);
    }

    private void addNewDialogs(RequestContext ctx) {
        Collection<Dialog> dialogs = ctx.getStorage().getCollection(DIALOGS_CONTEXT_FIELD_NAME);
        if (!dialogs.isEmpty()) {
            TelegramUUID uuid = ctx.getUserId();
            dialogs.forEach(dialog -> activeDialogs.put(uuid, dialog));
        }
    }
}
