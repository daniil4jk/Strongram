package ru.daniil4jk.strongram.handler.conditional.keyboard.registry;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface KeyboardSpecificButtonRegistry<KeyboardButton> {
    @NotNull
    @Contract("null -> fail")
    @SuppressWarnings("unchecked")
    static <Button> KeyboardSpecificButtonRegistry<Button> getByKeyboard(ReplyKeyboard keyboard) {
        if (keyboard instanceof ReplyKeyboardMarkup rkm) {
            return (KeyboardSpecificButtonRegistry<Button>) new ReplyKeyboardSpecificButtonRegistry(rkm);
        }
        if (keyboard instanceof InlineKeyboardMarkup ikm) {
            return (KeyboardSpecificButtonRegistry<Button>) new InlineKeyboardSpecificButtonRegistry(ikm);
        }
        throw new IllegalArgumentException("Unsupported keyboard type: " + keyboard.getClass().getName());
    }

    KeyboardButton getButtonByPayload(String payload);
}
