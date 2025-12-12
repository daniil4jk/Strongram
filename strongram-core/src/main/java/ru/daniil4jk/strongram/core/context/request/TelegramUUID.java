package ru.daniil4jk.strongram.core.context.request;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;

public record TelegramUUID(Chat chat, User user) {
    public @NotNull Long getChatId() {
        if (chat != null) {
            return chat.getId();
        } else if (user != null) {
            return user.getId();
        } else {
            throw new RuntimeException("this user and chat has`nt id`s");
        }
    }
}
