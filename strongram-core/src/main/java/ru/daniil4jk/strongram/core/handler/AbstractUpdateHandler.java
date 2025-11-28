package ru.daniil4jk.strongram.core.handler;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.function.TriConsumer;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.Nullable;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.core.context.BotContext;

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

    public AbstractUpdateHandler() {
    }

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
    }

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
}