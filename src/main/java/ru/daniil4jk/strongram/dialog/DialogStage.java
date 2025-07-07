package ru.daniil4jk.strongram.dialog;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.context.BotContext;

public interface DialogStage extends HasTriggerState {
    boolean canProcess(Update update);
    BotApiMethod<?> process(Update update, BotContext botContext, DialogContext dialogContext);
    BotApiMethod<?> onException(Exception e);
}
