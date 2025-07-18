package ru.daniil4jk.strongram;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;

public record TelegramUUID(Chat chat, User user) {
    public long getChatId() {
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
        return -1;
    }

    @NotNull
    public String getChatIdAsString() {
        return String.valueOf(getChatId());
    }

    private void throwException(String who, Exception e) {
        throw new IllegalStateException("Can`t get id, because %s has`nt id".formatted(who), e);
    }
}
