package ru.daniil4jk.strongram.core.dialog;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.core.context.BotContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ToString
@EqualsAndHashCode
public class DialogImpl implements Dialog {
    private final Map<String, DialogPart> parts;
    private final DialogContext context;

    @Builder
    public DialogImpl(@NonNull Map<String, DialogPart> parts, String initState) {
        this.parts = Collections.unmodifiableMap(parts);
        this.context = new DialogContextImpl(initState);
    }

    @Override
    public boolean canProcess(Update update) {
        return getCurrentPart() != null && getCurrentPart().filter(update);
    }

    @Override
    public BotApiMethod<?> process(Update update, BotContext botContext) {
        DialogPart currentStage = getCurrentPart();

        if (!currentStage.filter(update)) {
            throw new IllegalCallerException("dialog on stage %s can`t process this update"
                    .formatted(currentStage));
        }

        try {
            return currentStage.process(update, botContext, context);
        } catch (CannotProcessCaseException e) {
            throw e;
        } catch (Exception e) {
            return currentStage.onException(e);
        }
    }

    @Override
    public boolean isCompleted() {
        return context.isDialogCompleted();
    }

    private DialogPart getCurrentPart() {
        return parts.get(context.getState());
    }

    public static class DialogImplBuilder {
        private volatile Map<String, DialogPart> parts;

        public DialogImplBuilder stage(DialogPart part) {
            if (parts == null) {
                synchronized (this) {
                    if (parts == null) {
                        parts = new HashMap<>();
                    }
                }
            }

            parts.put(part.getOnState(), part);
            return this;
        }
    }
}
