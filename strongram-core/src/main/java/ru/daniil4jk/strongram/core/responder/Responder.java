package ru.daniil4jk.strongram.core.responder;

import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;

import java.util.List;

/**
 * Interface defining the contract for sending messages via a Telegram bot.
 * <p>
 * Implementations are responsible for processing and sending one or multiple
 * Telegram Bot API method calls with any type of content.
 * </p>
 */
public interface Responder {

    /**
     * Sends a list of Telegram Bot API method calls.
     * Saving original sending order.
     *
     * @param messages the list of API methods to send
     * @throws IllegalArgumentException if the provided list is null
     */
    void send(List<PartialBotApiMethod<?>> messages);

    /**
     * Sends a single Telegram Bot API method call.
     * Used for sending individual actions.
     *
     * @param message the API method to send
     * @throws IllegalArgumentException if the provided method is null
     */
    void send(PartialBotApiMethod<?> message);
}