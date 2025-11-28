package ru.daniil4jk.strongram.core.dto;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;

import java.util.concurrent.TimeUnit;

public record SendBotApiMethodDelayedTask(BotApiMethod<?> method, long mills) {
    public SendBotApiMethodDelayedTask(BotApiMethod<?> method, long value, TimeUnit timeUnit) {
        this(method, timeUnit.toMillis(value));
    }
}
