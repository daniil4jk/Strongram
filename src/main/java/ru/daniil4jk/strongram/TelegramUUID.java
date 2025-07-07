package ru.daniil4jk.strongram;

import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;

public record TelegramUUID(Chat chat, User user) {
    public long getChatId() {
        return chat != null ? chat.getId() : user.getId();
    }
}
