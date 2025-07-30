package ru.daniil4jk.strongram.core.dialog;

public interface DialogContext extends Cloneable {
    String getState();

    void setState(String state);

    boolean addObject(String objectName, Object object);

    <T> T getObjectCasted(String objectName, Class<T> clazz);

    boolean remove(String objectName);

    void setDialogCompleted();

    boolean isDialogCompleted();

    DialogContext clone();
}
