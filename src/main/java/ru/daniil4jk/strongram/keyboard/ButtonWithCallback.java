package ru.daniil4jk.strongram.keyboard;

public interface ButtonWithCallback {
    void prepareToAddingInRegistry();
    String getCallbackId();
    ButtonCallbackAction getCallbackAction();
}
