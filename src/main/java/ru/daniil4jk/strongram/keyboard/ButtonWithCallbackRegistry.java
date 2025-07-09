package ru.daniil4jk.strongram.keyboard;

import ru.daniil4jk.strongram.TelegramUUID;

public interface ButtonWithCallbackRegistry {
    boolean add(TelegramUUID uuid, ButtonWithCallback button);
    boolean contains(TelegramUUID uuid, String callbackData);
    ButtonWithCallback get(TelegramUUID uuid, String callbackData);
    boolean remove(TelegramUUID uuid, ButtonWithCallback button);
}
