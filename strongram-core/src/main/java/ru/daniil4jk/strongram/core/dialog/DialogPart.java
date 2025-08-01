package ru.daniil4jk.strongram.core.dialog;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.core.context.BotContext;

public interface DialogPart extends HasTriggerState {
    boolean filter(Update update);

    BotApiMethod<?> process(Update update, BotContext botContext, DialogContext dialogContext);

    BotApiMethod<?> onException(Exception e);
}
