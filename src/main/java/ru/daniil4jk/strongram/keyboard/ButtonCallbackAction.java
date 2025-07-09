package ru.daniil4jk.strongram.keyboard;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;

public interface ButtonCallbackAction {
    BotApiMethod<?> run();
    BotApiMethod<?> onException(Exception e);
    boolean isDisposable();
}
