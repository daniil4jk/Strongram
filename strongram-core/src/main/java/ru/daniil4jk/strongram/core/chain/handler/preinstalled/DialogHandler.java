package ru.daniil4jk.strongram.core.chain.handler.preinstalled;

import ru.daniil4jk.strongram.core.chain.context.Context;
import ru.daniil4jk.strongram.core.chain.filter.Filter;
import ru.daniil4jk.strongram.core.chain.handler.BaseHandler;
import ru.daniil4jk.strongram.core.dto.TelegramUUID;

import java.util.HashSet;
import java.util.Set;

public class DialogHandler extends BaseHandler {
    private final Set<TelegramUUID> inDialogUsers = new HashSet<>();

    @Override
    protected Filter getFilter() {
        return ctx -> inDialogUsers.contains(ctx.getUserId());
    }

    @Override
    protected void process(Context ctx) {
        super.process(ctx);
    }
}
