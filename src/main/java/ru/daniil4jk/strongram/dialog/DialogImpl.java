package ru.daniil4jk.strongram.dialog;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.context.BotContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ToString
@EqualsAndHashCode
public class DialogImpl implements Dialog {
    private final Map<String, DialogStage> stages;
    private DialogContext context;

    @Builder
    public DialogImpl(@NonNull Map<String, DialogStage> stages, String initState) {
        this.stages = Collections.unmodifiableMap(stages);
        this.context = new DialogContextImpl(initState);
    }

    @Override
    public boolean canProcess(Update update) {
        return getCurrentStage().canProcess(update);
    }

    @Override
    public BotApiMethod<?> process(Update update, BotContext botContext) {
        DialogStage currentStage = getCurrentStage();

        if (!currentStage.canProcess(update)) {
            throw new IllegalCallerException("dialog on stage %s can`t process this update"
                    .formatted(currentStage));
        }

        DialogContext contextOnStart = context.clone();

        try {
            return currentStage.process(update, botContext, context);
        } catch (Exception e) {
            BotApiMethod<?> failMessage = currentStage.onException(e);
            context = contextOnStart;
            return failMessage;
        }
    }

    @Override
    public boolean isCompleted() {
        return context.isDialogCompleted();
    }

    private DialogStage getCurrentStage() {
        return stages.get(context.getState());
    }

    public static class DialogImplBuilder {
        private volatile Map<String, DialogStage> stages;

        public DialogImplBuilder stage(DialogStage stage) {
            if (stages == null) {
                synchronized (this) {
                    if (stages == null) {
                        stages = new HashMap<>();
                    }
                }
            }

            stages.put(stage.getTriggerState(), stage);
            return this;
        }
    }
}
