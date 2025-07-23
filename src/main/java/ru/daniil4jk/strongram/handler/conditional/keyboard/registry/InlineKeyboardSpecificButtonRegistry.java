package ru.daniil4jk.strongram.handler.conditional.keyboard.registry;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Collection;

public class InlineKeyboardSpecificButtonRegistry extends
        AbstractKeyboardSpecificButtonRegistry<InlineKeyboardMarkup, InlineKeyboardButton> {

    public InlineKeyboardSpecificButtonRegistry(InlineKeyboardMarkup keyboard) {
        super(keyboard);
    }

    @Override
    public InlineKeyboardButton getButtonByPayload(String payload) {
        return getKeyboard().getKeyboard()
                .stream()
                .flatMap(Collection::stream)
                .filter(button -> payload.contains(button.getText()))
                .findFirst().orElseThrow();
    }
}
