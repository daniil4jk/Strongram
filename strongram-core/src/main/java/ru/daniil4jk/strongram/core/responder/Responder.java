package ru.daniil4jk.strongram.core.responder;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;

import java.util.List;

public interface Responder {
    void send(List<BotApiMethod<?>> messages);
}
