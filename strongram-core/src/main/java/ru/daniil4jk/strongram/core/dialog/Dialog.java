package ru.daniil4jk.strongram.core.dialog;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.core.context.BotContext;

public interface Dialog {
    String INIT_STATE = "init";

    boolean canProcess(Update update);

    BotApiMethod<?> process(Update update, BotContext botContext) throws CannotProcessCaseException;

    boolean isCompleted();
}
