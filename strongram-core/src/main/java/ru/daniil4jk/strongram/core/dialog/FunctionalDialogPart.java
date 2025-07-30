package ru.daniil4jk.strongram.core.dialog;

import lombok.*;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.core.context.BotContext;

import java.util.function.Function;
import java.util.function.Predicate;

@ToString
@EqualsAndHashCode
public class FunctionalDialogPart implements DialogPart {
    @Getter
    private final String onState;
    private final Predicate<Update> filter;
    private final DialogStageUpdateHandler process;
    private final Function<Exception, BotApiMethod<?>> exceptionHandler;

    @Builder
    public FunctionalDialogPart(@NonNull String onState, Predicate<Update> filter, @NonNull DialogStageUpdateHandler process, Function<Exception, BotApiMethod<?>> exceptionHandler) {
        this.onState = onState;
        this.filter = filter;
        this.process = process;
        this.exceptionHandler = exceptionHandler;
    }

    public FunctionalDialogPart(@NonNull String onState, @NonNull DialogStageUpdateHandler process) {
        this.onState = onState;
        this.filter = u -> true;
        this.process = process;
        this.exceptionHandler = e -> null;
    }

    @Override
    public boolean filter(Update update) {
        return filter.test(update);
    }

    @Override
    public BotApiMethod<?> process(Update update, BotContext botContext, DialogContext dialogContext) {
        return process.process(update, botContext, dialogContext);
    }

    @Override
    public BotApiMethod<?> onException(Exception e) {
        return exceptionHandler.apply(e);
    }

    @FunctionalInterface
    public interface DialogStageUpdateHandler {
        BotApiMethod<?> process(Update update, BotContext botContext, DialogContext dialogContext);
    }
}
