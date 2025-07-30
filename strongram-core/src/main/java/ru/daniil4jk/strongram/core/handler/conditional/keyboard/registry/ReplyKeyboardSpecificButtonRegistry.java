package ru.daniil4jk.strongram.core.handler.conditional.keyboard.registry;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.Collection;

public class ReplyKeyboardSpecificButtonRegistry extends
        AbstractKeyboardSpecificButtonRegistry<ReplyKeyboardMarkup, KeyboardButton> {

    public ReplyKeyboardSpecificButtonRegistry(ReplyKeyboardMarkup keyboard) {
        super(keyboard);
    }

    @Override
    public KeyboardButton getButtonByPayload(String payload) {
        return getKeyboard().getKeyboard()
                .stream()
                .flatMap(Collection::stream)
                .filter(button -> payload.contains(button.getText()))
                .findFirst().orElseThrow();
    }
}
