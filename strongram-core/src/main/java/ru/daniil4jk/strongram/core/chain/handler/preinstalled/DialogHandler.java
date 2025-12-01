package ru.daniil4jk.strongram.core.chain.handler.preinstalled;

import ru.daniil4jk.strongram.core.chain.context.Context;
import ru.daniil4jk.strongram.core.chain.filter.Filter;
import ru.daniil4jk.strongram.core.chain.filter.Filters;
import ru.daniil4jk.strongram.core.chain.filter.whitelist.HotUpdateableWhiteListFilter;
import ru.daniil4jk.strongram.core.chain.filter.whitelist.WhiteListFilter;
import ru.daniil4jk.strongram.core.chain.handler.BaseHandler;
import ru.daniil4jk.strongram.core.dto.TelegramUUID;

import java.util.concurrent.ForkJoinPool;

public class DialogHandler extends BaseHandler {
    private final WhiteListFilter<TelegramUUID> userInDialogFilter = new HotUpdateableWhiteListFilter<>(
            Filters::uuidEquals
    );

    @Override
    protected Filter getFilter() {
        return userInDialogFilter;
    }

    @Override
    protected void process(Context ctx) {
        super.process(ctx);
    }
}
