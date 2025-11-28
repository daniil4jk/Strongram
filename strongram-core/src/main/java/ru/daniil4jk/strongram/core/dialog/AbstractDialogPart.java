package ru.daniil4jk.strongram.core.dialog;

import lombok.*;
import org.apache.commons.lang3.function.TriFunction;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.core.context.BotContext;

import java.util.function.Function;
import java.util.function.Predicate;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public abstract class AbstractDialogPart implements DialogPart {
    private final String onState;

    @Override
    public boolean filter(Update update) {
        return true;
    }

    @Override
    public BotApiMethod<?> process(Update update, BotContext botContext, DialogContext dialogContext) {
        return null;
    }

    @Override
    public BotApiMethod<?> onException(Exception e) {
        if (e instanceof RuntimeException re) throw re;
        throw new RuntimeException(e);
    }

    @Getter
    @ToString
    @EqualsAndHashCode(callSuper = true)
    public static class Functional extends AbstractDialogPart {
        private final Predicate<Update> filter;
        private final DialogStageUpdateHandler process;
        private final Function<Exception, BotApiMethod<?>> exceptionHandler;

        public Functional(@NonNull String onState, @NonNull DialogStageUpdateHandler process) {
            super(onState);
            this.filter = super::filter;
            this.process = process;
            this.exceptionHandler = super::onException;
        }

        @Builder
        public Functional(@NonNull String onState, Predicate<Update> filter,
                          @NonNull DialogStageUpdateHandler process, Function<Exception,
                          BotApiMethod<?>> exceptionHandler) {
            super(onState);
            this.filter = filter;
            this.process = process;
            this.exceptionHandler = exceptionHandler;
        }

        @Override
        public boolean filter(Update update) {
            return filter.test(update);
        }

        @Override
        public BotApiMethod<?> process(Update update, BotContext botContext, DialogContext dialogContext) {
            return process.apply(update, botContext, dialogContext);
        }

        @Override
        public BotApiMethod<?> onException(Exception e) {
            return exceptionHandler.apply(e);
        }

        @FunctionalInterface
        public interface DialogStageUpdateHandler extends TriFunction<Update, BotContext, DialogContext, BotApiMethod<?>> {}
    }
}
