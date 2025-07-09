package ru.daniil4jk.strongram.dialog;

import lombok.*;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.context.BotContext;

import java.util.function.Function;
import java.util.function.Predicate;

@Builder
@ToString
@EqualsAndHashCode
public class DialogStageImpl implements DialogStage {
    @Getter
    @NonNull
    private final String triggerState;
    @Builder.Default
    private final Predicate<Update> canProcess = u -> true;
    @NonNull
    private final DialogStageUpdateHandler updateHandler;
    @Builder.Default
    private final Function<Exception, BotApiMethod<?>> exceptionHandler = e -> null;

    public DialogStageImpl(@NonNull String triggerState, Predicate<Update> canProcess, @NonNull DialogStageUpdateHandler updateHandler, Function<Exception, BotApiMethod<?>> exceptionHandler) {
        this.triggerState = triggerState;
        this.canProcess = canProcess;
        this.updateHandler = updateHandler;
        this.exceptionHandler = exceptionHandler;
    }

    public DialogStageImpl(@NonNull String triggerState, @NonNull DialogStageUpdateHandler updateHandler) {
        this.triggerState = triggerState;
        this.canProcess = u -> true;
        this.updateHandler = updateHandler;
        this.exceptionHandler = e -> null;
    }

    @Override
    public boolean canProcess(Update update) {
        return canProcess.test(update);
    }

    @Override
    public BotApiMethod<?> process(Update update, BotContext botContext, DialogContext dialogContext) {
        return updateHandler.process(update, botContext, dialogContext);
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
