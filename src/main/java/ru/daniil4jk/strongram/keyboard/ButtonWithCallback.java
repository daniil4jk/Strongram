package ru.daniil4jk.strongram.keyboard;

public interface ButtonWithCallback {
    String getCallbackId();
    void callback();
    void onException(Exception e);
    boolean isRemoveOnException();
}
