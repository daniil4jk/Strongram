package ru.daniil4jk.strongram.keyboard;

public interface ButtonWithCallback {
    void prepareToAddingInRegistry();
    String getCallbackId();
    void callback();
    void onException(Exception e);
    boolean isRemoveOnException();
}
