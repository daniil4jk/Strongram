package ru.daniil4jk.simple_tg_lib;

public record TelegramUUID(Long chatId, Long userId) {
    public long getChatId() {
        return chatId != null ? chatId : userId;
    }
}
