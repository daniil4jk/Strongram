package ru.daniil4jk.simple_tg_lib.handler.one_time;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface OneTimeHandler {
    BotApiMethodMessage firstNotification();
    BotApiMethodMessage notification();
    boolean canProcess(Update update);
    void process(Update update);
    void onException(Exception e);
    boolean removeOnException();
}
