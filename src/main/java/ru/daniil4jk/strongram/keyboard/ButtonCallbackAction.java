package ru.daniil4jk.strongram.keyboard;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.TelegramUUID;
import ru.daniil4jk.strongram.context.BotContext;

public interface ButtonCallbackAction {
    BotApiMethod<?> process(Update update, TelegramUUID uuid, BotContext botContext);
    BotApiMethod<?> onException(Exception e);
    boolean isDisposable();
}
