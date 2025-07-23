package ru.daniil4jk.strongram.handler.conditional.keyboard.pattern;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Collection;
import java.util.stream.Collectors;

public class InlineKeyboardPatternGenerator extends AbstractKeyboardPatternGenerator {
    public InlineKeyboardPatternGenerator(@NotNull InlineKeyboardMarkup keyboard) {
        super(keyboard.getKeyboard().stream()
                .flatMap(Collection::stream)
                .map(InlineKeyboardButton::getCallbackData)
                .collect(Collectors.toList()));
    }
}
