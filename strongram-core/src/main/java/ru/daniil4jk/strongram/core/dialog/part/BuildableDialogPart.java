package ru.daniil4jk.strongram.core.dialog.part;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ru.daniil4jk.strongram.core.context.dialog.DialogContext;
import ru.daniil4jk.strongram.core.context.request.RequestContext;
import ru.daniil4jk.strongram.core.filter.Filter;
import ru.daniil4jk.strongram.core.filter.Filters;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@ToString
@EqualsAndHashCode
public class BuildableDialogPart<ENUM extends Enum<ENUM>> implements DialogPart<ENUM> {
    private final NotificationManager<ENUM> notificationManager;
    private final Filter filter;
    private final TryProcess<ENUM> handler;
    private final Consumer<Exception> onException;
    private DialogContext<ENUM> dCtx;

    public BuildableDialogPart(Class<ENUM> enumClass,
                               Filter filter,
                               Consumer<RequestContext> firstNotification,
                               BiConsumer<RequestContext, DialogContext<ENUM>> repeatNotification,
                               TryProcess<ENUM> handler,
                               Consumer<Exception> onException) {
        this.filter = filter;
        this.handler = handler;
        this.onException = onException;
        this.notificationManager = new NotificationManager<>(
                firstNotification,
                repeatNotification
        );
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
    public void sendNotification(RequestContext ctx) {
        notificationManager.sendNotification(ctx, dCtx);
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

    @Contract(value = " -> new", pure = true)
    public static <ENUM extends Enum<ENUM>> @NotNull Builder<ENUM> builder() {
        return new Builder<>();
    }

    public static class Builder<ENUM extends Enum<ENUM>> {
        private static final Filter DEFAULT_FILTER = Filters.acceptAll();
        private static final Consumer<Exception> DEFAULT_ON_EXCEPTION = e -> {
            if (e instanceof RuntimeException re) {
                throw re;
            } else {
                throw new RuntimeException(e);
            }
        };

        private Class<ENUM> enumClass;
        private Consumer<RequestContext> firstNotification;
        private BiConsumer<RequestContext, DialogContext<ENUM>> repeatNotification;
        private Filter filter;
        private TryProcess<ENUM> handler;
        private Consumer<Exception> onException;

        public Builder<ENUM> enumClass(Class<ENUM> enumClass) {
            this.enumClass = enumClass;
            return this;
        }

        public Builder<ENUM> firstNotification(Consumer<RequestContext> function) {
            this.firstNotification = function;
            return this;
        }

        public Builder<ENUM> firstNotification(String string) {
            this.firstNotification = ctx -> ctx.respond(string);
            return this;
        }

        public Builder<ENUM> filter(Filter filter) {
            this.filter = filter;
            return this;
        }

        public Builder<ENUM> repeatNotification(BiConsumer<RequestContext, DialogContext<ENUM>> function) {
            this.repeatNotification = function;
            return this;
        }

        public Builder<ENUM> repeatNotification(String string) {
            this.repeatNotification = (rCtx, dCtx) -> rCtx.respond(string);
            return this;
        }

        public Builder<ENUM> handler(TryProcess<ENUM> handler) {
            this.handler = handler;
            return this;
        }

        public Builder<ENUM> onException(Consumer<Exception> onException) {
            this.onException = onException;
            return this;
        }

        public BuildableDialogPart<ENUM> build() {
            checkRules();
            return new BuildableDialogPart<>(enumClass, filter, firstNotification, repeatNotification, handler, onException);
        }

        private void checkRules() {
            if (filter == null) {
                filter = DEFAULT_FILTER;
            }

            if (onException == null) {
                onException = DEFAULT_ON_EXCEPTION;
            }
        }
    }
}
