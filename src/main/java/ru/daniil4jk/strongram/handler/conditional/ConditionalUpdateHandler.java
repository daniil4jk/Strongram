package ru.daniil4jk.strongram.handler.conditional;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.function.TriConsumer;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.Nullable;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.context.BotContext;
import ru.daniil4jk.strongram.handler.AbstractUpdateHandler;
import ru.daniil4jk.strongram.parser.ParserService;
import ru.daniil4jk.strongram.parser.payload.PayloadParserService;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.regex.Pattern;


@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class ConditionalUpdateHandler extends AbstractUpdateHandler {
    private static final ParserService<String> payloadParser = PayloadParserService.getInstance();
    private final BiPredicate<Update, BotContext> filter;

    public ConditionalUpdateHandler(BiPredicate<Update, BotContext> filter) {
        this.filter = filter;
    }

    public ConditionalUpdateHandler(Pattern pattern) {
        this.filter = patternToFilter(pattern);
    }

    @Override
    public BotApiMethod<?> process(Update update, BotContext context) {
        if (filter.test(update, context)) {
            return super.process(update, context);
        } else {
            return processNext(update, context);
        }
    }

    public static BiPredicate<Update, BotContext> patternToFilter(Pattern pattern) {
        return (upd, ctx) -> pattern.matcher(
                payloadParser.parse(upd)
        ).matches();
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode(callSuper = true)
    public static class Functional extends ConditionalUpdateHandler {
        private static final ConditionalUpdateHandler DEFAULT_HANDLER = new ConditionalUpdateHandler((upd, ctx) -> true);

        private final BiConsumer<Update, BotContext> beforeExecute;
        private final BiFunction<Update, BotContext, BotApiMethod<?>> execute;
        private final BiConsumer<Update, BotContext> beforeProcessNext;
        private final TriFunction<Update, BotContext, Exception, BotApiMethod<?>> onException;
        private final TriConsumer<Update, BotContext, BotApiMethod<?>> afterExecute;

        public Functional(Pattern pattern) {
            this(patternToFilter(pattern));
        }

        public Functional(BiPredicate<Update, BotContext> filter) {
            this(
                    filter,
                    DEFAULT_HANDLER::beforeExecute,
                    DEFAULT_HANDLER::execute,
                    DEFAULT_HANDLER::beforeProcessNext,
                    DEFAULT_HANDLER::onException,
                    DEFAULT_HANDLER::afterExecute
            );
        }

        public Functional(Pattern pattern,
                          BiConsumer<Update, BotContext> beforeExecute,
                          BiFunction<Update, BotContext, BotApiMethod<?>> execute,
                          BiConsumer<Update, BotContext> beforeProcessNext,
                          TriFunction<Update, BotContext, Exception, BotApiMethod<?>> onException,
                          TriConsumer<Update, BotContext, BotApiMethod<?>> afterExecute) {
            this(
                    pattern,
                    null,
                    beforeExecute,
                    execute,
                    beforeProcessNext,
                    onException,
                    afterExecute
            );
        }

        public Functional(BiPredicate<Update, BotContext> filter,
                          BiConsumer<Update, BotContext> beforeExecute,
                          BiFunction<Update, BotContext, BotApiMethod<?>> execute,
                          BiConsumer<Update, BotContext> beforeProcessNext,
                          TriFunction<Update, BotContext, Exception, BotApiMethod<?>> onException,
                          TriConsumer<Update, BotContext, BotApiMethod<?>> afterExecute) {
            this(
                    null,
                    filter,
                    beforeExecute,
                    execute,
                    beforeProcessNext,
                    onException,
                    afterExecute
            );
        }

        @Builder
        private Functional(Pattern pattern,
                           BiPredicate<Update, BotContext> filter,
                           BiConsumer<Update, BotContext> beforeExecute,
                           BiFunction<Update, BotContext, BotApiMethod<?>> execute,
                           BiConsumer<Update, BotContext> beforeProcessNext,
                           TriFunction<Update, BotContext, Exception, BotApiMethod<?>> onException,
                           TriConsumer<Update, BotContext, BotApiMethod<?>> afterExecute) {
            super(getFilterOfTwoParams(pattern, filter));
            this.beforeExecute = Optional.ofNullable(beforeExecute).orElse(DEFAULT_HANDLER::beforeExecute);
            this.execute = Optional.ofNullable(execute).orElse(DEFAULT_HANDLER::execute);
            this.beforeProcessNext = Optional.ofNullable(beforeProcessNext).orElse(DEFAULT_HANDLER::beforeProcessNext);
            this.onException = Optional.ofNullable(onException).orElse(DEFAULT_HANDLER::onException);
            this.afterExecute = Optional.ofNullable(afterExecute).orElse(DEFAULT_HANDLER::afterExecute);
        }

        private static BiPredicate<Update, BotContext> getFilterOfTwoParams(Pattern pattern,
                                                                            BiPredicate<Update, BotContext> filter) {
            if (filter != null && pattern != null) {
                return filter.and(patternToFilter(pattern));
            }
            if (filter != null) return filter;
            if (pattern != null) return patternToFilter(pattern);
            throw new NullPointerException("Requires at least either a pattern or a filter");
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
