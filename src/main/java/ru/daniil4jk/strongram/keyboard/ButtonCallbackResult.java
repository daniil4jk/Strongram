package ru.daniil4jk.strongram.keyboard;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;

public record ButtonCallbackResult(BotApiMethod<?> response) {
}
