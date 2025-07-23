package ru.daniil4jk.strongram.handler.conditional.keyboard.pattern;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.regex.Pattern;

public interface KeyboardPatternGenerator {
    @NotNull
    @Contract("null -> fail")
    static KeyboardPatternGenerator getByKeyboard(ReplyKeyboard keyboard) {
        if (keyboard instanceof ReplyKeyboardMarkup rkm) {
            return new ReplyKeyboardPatternGenerator(rkm);
        }
        if (keyboard instanceof InlineKeyboardMarkup ikm) {
            return new InlineKeyboardPatternGenerator(ikm);
        }
        throw new IllegalArgumentException("Unsupported keyboard type: " + keyboard.getClass().getName());
    }

    Pattern getPattern();
}
