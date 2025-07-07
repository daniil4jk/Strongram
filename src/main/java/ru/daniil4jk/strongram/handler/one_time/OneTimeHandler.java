package ru.daniil4jk.strongram.handler.one_time;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.context.BotContext;

public interface OneTimeHandler {
    BotApiMethodMessage getFirstNotification();
    BotApiMethodMessage getNotification();
    boolean filter(Update update, BotContext context);
    BotApiMethod<?> process(Update update, BotContext context);
    void onException(Exception e);
    boolean isRemoveOnException();
}
