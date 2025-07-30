package ru.daniil4jk.strongram.core.handler.conditional.keyboard.pattern;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.regex.Pattern;

public interface KeyboardRegexPatternGenerator {
    @NotNull
    @Contract("null -> fail")
    static KeyboardRegexPatternGenerator getByKeyboard(ReplyKeyboard keyboard) {
        if (keyboard instanceof ReplyKeyboardMarkup rkm) {
            return new ReplyKeyboardRegexPatternGenerator(rkm);
        }
        if (keyboard instanceof InlineKeyboardMarkup ikm) {
            return new InlineKeyboardRegexPatternGenerator(ikm);
        }
        throw new IllegalArgumentException("Unsupported keyboard type: " + keyboard.getClass().getName());
    }

    Pattern getPattern();
}
