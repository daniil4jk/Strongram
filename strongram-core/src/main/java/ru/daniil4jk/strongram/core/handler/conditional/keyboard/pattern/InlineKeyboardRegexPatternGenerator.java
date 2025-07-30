package ru.daniil4jk.strongram.core.handler.conditional.keyboard.pattern;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Collection;

public class InlineKeyboardRegexPatternGenerator extends AbstractKeyboardRegexPatternGenerator {
    public InlineKeyboardRegexPatternGenerator(@NotNull InlineKeyboardMarkup keyboard) {
        super(keyboard.getKeyboard().stream()
                .flatMap(Collection::stream)
                .map(InlineKeyboardButton::getCallbackData));
    }
}
