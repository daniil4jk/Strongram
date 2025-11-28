package ru.daniil4jk.strongram.core.dialog;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.core.context.BotContext;

import java.util.*;

@ToString
@EqualsAndHashCode
public class AbstractDialog implements Dialog {
    private final Map<String, DialogPart> partsMap;
    private final DialogContext context;

    public AbstractDialog(@NotNull Collection<DialogPart> parts) {
        this(parts, Dialog.INIT_STATE);
    }

    @Builder
    public AbstractDialog(@NotNull @Singular Collection<DialogPart> parts, String initState) {
        Map<String, DialogPart> partsMap = new HashMap<>();

        for (DialogPart part : parts) {
            partsMap.compute(part.getOnState(), (k, p) -> {
                if (p == null) return part;
                if (p.equals(part)) return p;
                throw new IllegalStateException("cannot link part %s with key %s, " +
                        "because part %s already linked with this key");
            });
        }

        this.partsMap = Collections.unmodifiableMap(partsMap);
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
        return partsMap.get(context.getState());
    }
}
