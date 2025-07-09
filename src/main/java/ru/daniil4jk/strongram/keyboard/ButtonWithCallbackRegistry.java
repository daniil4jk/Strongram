package ru.daniil4jk.strongram.keyboard;

public interface ButtonWithCallbackRegistry {
    boolean add(ButtonWithCallback button, boolean temporarily);
    boolean contains(String callbackData);
    ButtonCallbackAction get(String callbackData);
    boolean remove(ButtonWithCallback button);
    boolean remove(String callbackId, ButtonCallbackAction action);
}
