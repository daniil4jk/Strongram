package ru.daniil4jk.strongram.core.handler.conditional.keyboard.registry;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Getter
public abstract class AbstractKeyboardSpecificButtonRegistry<Keyboard extends ReplyKeyboard, KeyboardButton>
        implements KeyboardSpecificButtonRegistry<KeyboardButton> {
    private final Keyboard keyboard;

    public AbstractKeyboardSpecificButtonRegistry(Keyboard keyboard) {
        this.keyboard = keyboard;
    }
}
