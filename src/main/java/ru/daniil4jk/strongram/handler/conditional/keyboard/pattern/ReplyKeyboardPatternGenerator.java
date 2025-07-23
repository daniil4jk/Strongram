package ru.daniil4jk.strongram.handler.conditional.keyboard.pattern;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.Collection;

public class ReplyKeyboardPatternGenerator extends AbstractKeyboardPatternGenerator {
    public ReplyKeyboardPatternGenerator(@NotNull ReplyKeyboardMarkup keyboard) {
        super(keyboard.getKeyboard().stream()
                .flatMap(Collection::stream)
                .map(KeyboardButton::getText));
    }
}
