package ru.daniil4jk.strongram.core.context.request;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;

/**
 * Immutable container for Telegram chat and user context.
 * Used to determine the target chat ID for bot replies.
 */
public record TelegramUUID(Chat chat, User user) {

    /**
     * Returns the chat ID to reply to.
     * <p>
     * Priority is given to the chat ID. If chat is null, user ID is used.
     * </p>
     *
     * @return the chat or user ID
     * @throws IllegalStateException if both chat and user are null
     */
    public @NotNull Long getReplyChatId() throws IllegalStateException {
        if (chat != null) {
            return chat.getId();
        } else if (user != null) {
            return user.getId();
        } else {
            throw new IllegalStateException("this update contained entity has no user or chat id`s");
        }
    }
}