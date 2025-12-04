package ru.daniil4jk.strongram.core.dialog;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import ru.daniil4jk.strongram.core.chain.context.RequestContext;
import ru.daniil4jk.strongram.core.chain.filter.Filter;
import ru.daniil4jk.strongram.core.dialog.state.DialogContext;

import java.util.function.Consumer;
import java.util.function.Function;

@ToString
@EqualsAndHashCode
public class BuildableDialogPart<ENUM extends Enum<ENUM>> implements DialogPart<ENUM> {
    private final Function<DialogContext<ENUM>, BotApiMethod<?>> firstAsk;
    private final Filter filter;
    private final Function<DialogContext<ENUM>, BotApiMethod<?>> repeatAsk;
    private final TryProcess<ENUM> handler;
    private final Consumer<Exception> onException;
    private DialogContext<ENUM> dCtx;

    @Builder
    protected BuildableDialogPart(Class<ENUM> enumClass,
                                  Function<DialogContext<ENUM>, BotApiMethod<?>> firstAsk,
                                  Filter filter,
                                  Function<DialogContext<ENUM>, BotApiMethod<?>> repeatAsk,
                                  TryProcess<ENUM> handler,
                                  Consumer<Exception> onException) {
        this.firstAsk = firstAsk;
        this.filter = filter;
        this.repeatAsk = repeatAsk;
        this.handler = handler;
        this.onException = onException;
    }

    @Override
    public void accept(RequestContext rCtx) {
        try {
            handler.accept(rCtx, dCtx);
        } catch (Exception e) {
            onException.accept(e);
        }
    }

    @Override
    public BotApiMethod<?> firstAsk() {
        return firstAsk.apply(dCtx);
    }

    @Override
    public BotApiMethod<?> repeatAsk() {
        return repeatAsk.apply(dCtx);
    }

    @Override
    public boolean canAccept(RequestContext ctx) {
        return filter.test(ctx);
    }

    @Override
    public void injectDialogContext(DialogContext<ENUM> dCtx) {
        this.dCtx = dCtx;
    }

    public interface TryProcess<ENUM extends Enum<ENUM>> {
        void accept(RequestContext rCtx, DialogContext<ENUM> dCtx) throws Exception;
    }
}
