package ru.daniil4jk.strongram.core.handler.conditional.keyboard;

public interface ButtonActionRegistry<Button> {
    boolean addAction(Button button, KeyboardUpdateHandler.ButtonAction action);

    boolean exists(Button button);

    KeyboardUpdateHandler.ButtonAction getAction(Button button);
}
