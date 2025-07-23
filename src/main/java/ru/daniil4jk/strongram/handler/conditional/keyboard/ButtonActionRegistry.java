package ru.daniil4jk.strongram.handler.conditional.keyboard;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import ru.daniil4jk.strongram.TelegramUUID;

import java.util.function.BiFunction;

public interface ButtonActionRegistry<Button> {
    boolean addAction(Button button, KeyboardUpdateHandler.ButtonAction action);
    boolean exists(Button button);
    KeyboardUpdateHandler.ButtonAction getAction(Button button);
}
