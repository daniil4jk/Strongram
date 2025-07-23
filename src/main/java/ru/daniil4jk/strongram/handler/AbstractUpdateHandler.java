package ru.daniil4jk.strongram.handler;

import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.function.TriConsumer;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.Nullable;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.context.BotContext;
import ru.daniil4jk.strongram.handler.conditional.keyboard.KeyboardUpdateHandler;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@Slf4j
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class AbstractUpdateHandler implements UpdateHandler {
    private UpdateHandler next;

    public AbstractUpdateHandler() {}

    @Override
    public BotApiMethod<?> process(Update update, BotContext context) {
        BotApiMethod<?> result = null;
        try {
            beforeExecute(update, context);
            result = execute(update, context);
        } catch (Exception e) {
            result = onException(update, context, e);
        } finally {
            afterExecute(update, context, result);
        }
        return result;
    }

    protected void beforeExecute(Update update, BotContext context) {
        if (log.isDebugEnabled()) {
            log.debug("Update {} processing by {}",
                    update.getUpdateId(), this.getClass().getSimpleName());
        }
    }

    protected BotApiMethod<?> execute(Update update, BotContext context) {
        return processNext(update, context);
    };

    protected void beforeProcessNext(Update update, BotContext context) {

    }

    protected BotApiMethod<?> processNext(Update update, BotContext context) {
        if (next == null) {
            return null;
        }
        beforeProcessNext(update, context);
        return next.process(update, context);
    }

    protected BotApiMethod<?> onException(Update update, BotContext context, Exception e) {
        if (log.isDebugEnabled()) {
            log.debug("onException is not override in {}. Exception + {} thrown from it",
                    this.getClass().getSimpleName(), String.valueOf(e));
        }
        if (e instanceof RuntimeException re) {
            throw re;
        } else {
            throw new RuntimeException(e);
        }
    }

    protected void afterExecute(Update update, BotContext context, @Nullable BotApiMethod<?> result) {

    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode(callSuper = true)
    public static class Functional extends AbstractUpdateHandler {
        private static final AbstractUpdateHandler DEFAULT_HANDLER = new AbstractUpdateHandler();

        private final BiConsumer<Update, BotContext> beforeExecute;
        private final BiFunction<Update, BotContext, BotApiMethod<?>> execute;
        private final BiConsumer<Update, BotContext> beforeProcessNext;
        private final TriFunction<Update, BotContext, Exception, BotApiMethod<?>> onException;
        private final TriConsumer<Update, BotContext, BotApiMethod<?>> afterExecute;

        public Functional() {
            this(DEFAULT_HANDLER::execute);
        }

        public Functional(BiFunction<Update, BotContext, BotApiMethod<?>> execute) {
            this(
                    DEFAULT_HANDLER::beforeExecute,
                    execute,
                    DEFAULT_HANDLER::beforeProcessNext,
                    DEFAULT_HANDLER::onException,
                    DEFAULT_HANDLER::afterExecute
            );
        }

        @Builder
        public Functional(BiConsumer<Update, BotContext> beforeExecute,
                          BiFunction<Update, BotContext, BotApiMethod<?>> execute,
                          BiConsumer<Update, BotContext> beforeProcessNext,
                          TriFunction<Update, BotContext, Exception, BotApiMethod<?>> onException,
                          TriConsumer<Update, BotContext, BotApiMethod<?>> afterExecute) {
            this.beforeExecute = Optional.ofNullable(beforeExecute).orElse(DEFAULT_HANDLER::beforeExecute);
            this.execute = Optional.ofNullable(execute).orElse(DEFAULT_HANDLER::execute);
            this.beforeProcessNext = Optional.ofNullable(beforeProcessNext).orElse(DEFAULT_HANDLER::beforeProcessNext);
            this.onException = Optional.ofNullable(onException).orElse(DEFAULT_HANDLER::onException);
            this.afterExecute = Optional.ofNullable(afterExecute).orElse(DEFAULT_HANDLER::afterExecute);
        }

        @Override
        protected void beforeExecute(Update update, BotContext context) {
            beforeExecute.accept(update, context);
        }

        @Override
        protected BotApiMethod<?> execute(Update update, BotContext context) {
            return execute.apply(update, context);
        }

        @Override
        protected void beforeProcessNext(Update update, BotContext context) {
            beforeProcessNext.accept(update, context);
        }

        @Override
        protected BotApiMethod<?> onException(Update update, BotContext context, Exception e) {
            return onException.apply(update, context, e);
        }

        @Override
        protected void afterExecute(Update update, BotContext context, @Nullable BotApiMethod<?> result) {
            afterExecute.accept(update, context, result);
        }
    }
}