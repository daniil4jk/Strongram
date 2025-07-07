package ru.daniil4jk.strongram.keyboard;

public interface ButtonWithCallbackRegistry {
    boolean add(ButtonWithCallback button);
    boolean contains(String callbackData);
    ButtonWithCallback get(String callbackData);
    boolean remove(ButtonWithCallback button);
}
