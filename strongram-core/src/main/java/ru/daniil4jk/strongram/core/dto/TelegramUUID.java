package ru.daniil4jk.strongram.core.dto;

import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;

public record TelegramUUID(Chat chat, User user) {
    public Long getChatId() {
        if (chat != null) {
            try {
                return chat.getId();
            } catch (Exception e) {
                throwException("chat", e);
            }
        } else {
            try {
                return user.getId();
            } catch (Exception e) {
                throwException("user", e);
            }
        }
        return (long) -1; //unreachable
    }

    private static void throwException(String who, Exception e) {
        throw new IllegalStateException("Can`t get id, because %s has`nt id".formatted(who), e);
    }
}
