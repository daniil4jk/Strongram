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
@AllArgsConstructor
@RequiredArgsConstructor
public class DialogStageImpl implements DialogStage {
    @Getter
    @NonNull
    private String triggerState;
    private Predicate<Update> canProcess;
    private DialogStageUpdateHandler updateHandler;
    private Function<Exception, BotApiMethod<?>> exceptionHandler;

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
