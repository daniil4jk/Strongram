package ru.daniil4jk.strongram.core.context.request;

import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.daniil4jk.strongram.core.bot.Bot;
import ru.daniil4jk.strongram.core.context.storage.Storage;
import ru.daniil4jk.strongram.core.unboxer.Unboxer;

import java.io.File;
import java.util.List;

/**
 * Represents the context of an incoming Telegram update, providing access to request data,
 * response handling, storage, and bot instance.
 *
 * <p>This interface allows handlers to interact with the current update, send one or more responses,
 * access user-specific storage, and retrieve the associated bot instance. It serves as the central
 * point for processing Telegram messages and other update types.</p>
 */
public interface RequestContext {

    /**
     * Gets the list of response methods that have been prepared to be sent back to the user.
     *
     * @return a list of {@link PartialBotApiMethod} instances representing outgoing messages or actions
     */
    List<PartialBotApiMethod<?>> getResponses();

    /**
     * Adds a response to be sent in reply to the current request.
     *
     * @param response the {@link PartialBotApiMethod} to be sent, such as a message or media
     */
    void respond(PartialBotApiMethod<?> response);

    /**
     * Sends a plain text message in response to the current request.
     *
     * @param text the text content of the message to send
     */
    void respond(String text);

    /**
     * Sends a text message with a custom reply keyboard.
     *
     * @param text     the text content of the message
     * @param keyboard the {@link ReplyKeyboard} to be attached to the message (e.g. inline or reply keyboard)
     */
    void respond(String text, ReplyKeyboard keyboard);

    /**
     * Sends a message with text and a file attachment of a specified media type.
     * Simplify using SendPhoto, SendVideo, SendAudio, SendVoice, SendDocument.
     *
     * @param text the caption or text associated with the file
     * @param file the file to send
     * @param type the type of media, used to determine how file should be parsed
     */
    void respond(String text, File file, MediaType type);

    /**
     * Retrieves a unique identifier for this request context, typically tied to the user or chat.
     *
     * @return the {@link TelegramUUID} representing the logical session or user
     */
    TelegramUUID getUUID();

    /**
     * Gets the original Telegram {@link Update} object that triggered this context.
     *
     * @return the raw {@link Update} instance from Telegram Bot API
     */
    Update getRequest();

    /**
     * Extracts a specific type of request data using a provided unboxer.
     *
     * @param unboxer the {@link Unboxer} implementation that converts the update into a desired type
     * @param <T>     the type of object to extract (e.g., Message, CallbackQuery)
     * @return an instance of type {@code T} derived from the update
     */
    <T> T getRequest(Unboxer<T> unboxer);

    /**
     * Provides access to persistent or in-memory storage associated with this context.
     *
     * <p>This storage can be used to save and retrieve user or session-specific data across interactions.</p>
     *
     * @return the {@link Storage} instance linked to this context
     */
    Storage getStorage();

    /**
     * Returns the bot instance who calls this handler.
     *
     * @return the {@link Bot} that is processing this update
     */
    Bot getBot();

    enum MediaType {
        Photo,
        Video,
        Audio,
        Voice,
        Document
    }
}