package ru.daniil4jk.strongram.dialog;

public interface DialogContext extends Cloneable {
    void setState(String state);
    String getState();
    boolean addObject(String objectName, Object object);
    <T> T getObjectCasted(String objectName, Class<T> clazz);
    boolean remove(String objectName);
    <T> T removeAndGet(String objectName, Class<T> clazz);
    void setDialogCompleted();
    boolean isDialogCompleted();
    DialogContext clone();
}
