package ru.daniil4jk.strongram.handler;

import lombok.*;
import org.apache.commons.lang3.function.TriConsumer;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.Nullable;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.context.BotContext;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
public class FunctionalUpdateHandler extends AbstractUpdateHandler {
    private static final AbstractUpdateHandler defaultAbstractUpdateHandler = new AbstractUpdateHandler() {};

    @Builder.Default
    private final BiConsumer<Update, BotContext> beforeExecute =
            defaultAbstractUpdateHandler::beforeExecute;
    @Builder.Default
    private final BiFunction<Update, BotContext, BotApiMethod<?>> execute =
            defaultAbstractUpdateHandler::execute;
    @Builder.Default
    private final BiConsumer<Update, BotContext> beforeProcessNext =
            defaultAbstractUpdateHandler::beforeProcessNext;
    @Builder.Default
    private final TriFunction<Update, BotContext, Exception, BotApiMethod<?>> onException =
            defaultAbstractUpdateHandler::onException;
    @Builder.Default
    private final TriConsumer<Update, BotContext, BotApiMethod<?>> afterExecute =
            defaultAbstractUpdateHandler::afterExecute;

    public FunctionalUpdateHandler() {
        this(defaultAbstractUpdateHandler::execute);
    }

    public FunctionalUpdateHandler(BiFunction<Update, BotContext, BotApiMethod<?>> execute) {
        this(
             defaultAbstractUpdateHandler::beforeExecute,
             execute,
             defaultAbstractUpdateHandler::beforeProcessNext,
             defaultAbstractUpdateHandler::onException,
             defaultAbstractUpdateHandler::afterExecute
        );
    }

    public FunctionalUpdateHandler(BiConsumer<Update, BotContext> beforeExecute,
                                   BiFunction<Update, BotContext, BotApiMethod<?>> execute,
                                   BiConsumer<Update, BotContext> beforeProcessNext,
                                   TriFunction<Update, BotContext, Exception, BotApiMethod<?>> onException,
                                   TriConsumer<Update, BotContext, BotApiMethod<?>> afterExecute) {
        this.beforeExecute = beforeExecute;
        this.execute = execute;
        this.beforeProcessNext = beforeProcessNext;
        this.onException = onException;
        this.afterExecute = afterExecute;
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