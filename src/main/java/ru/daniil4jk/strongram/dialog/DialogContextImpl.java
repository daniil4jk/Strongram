package ru.daniil4jk.strongram.dialog;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@ToString
@EqualsAndHashCode
public class DialogContextImpl implements DialogContext {
    private final AtomicReference<String> state = new AtomicReference<>();
    private final AtomicBoolean dialogCompleted = new AtomicBoolean(false);
    private volatile Map<String, Object> objectMap;

    public DialogContextImpl(String initState) {
        state.set(initState);
    }

    private DialogContextImpl(Map<String, Object> objectMap, String state, boolean dialogCompleted) {
        this.objectMap = objectMap;
        this.state.set(state);
        this.dialogCompleted.set(dialogCompleted);
    }

    private void createMapIfAbsent() {
        if (objectMap == null) {
            synchronized (this) {
                if (objectMap == null) {
                    objectMap = new HashMap<>();
                }
            }
        }
    }

    @Override
    public String getState() {
        return this.state.get();
    }

    @Override
    public void setState(String state) {
        this.state.set(state);
    }

    @Override
    public boolean addObject(String objectName, Object object) {
        createMapIfAbsent();
        try {
            objectMap.compute(objectName, (on, o) -> {
                if (o != null) {
                    String what;
                    if (o == object) {

                        what = "this";
                    } else {
                        what = "another";
                    }

                    throw new IllegalStateException("%s object already exist by this key"
                            .formatted(what));
                }
                return object;
            });

            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    @Override
    public <T> T getObjectCasted(String objectName, Class<T> clazz) {
        if (objectMap == null) return null;
        try {
            return (T) objectMap.get(objectName);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("the key %s contains an object of class %s, but not of class %s"
                    .formatted(objectName, objectMap.get(objectName).getClass().getName(), clazz.getName()));
        }
    }

    @Override
    public boolean remove(String objectName) {
        if (objectMap == null) return false;
        return objectMap.remove(objectName) != null;
    }

    @Override
    public void setDialogCompleted() {
        if (!this.dialogCompleted.compareAndSet(false, true)) {
            throw new IllegalStateException("Dialog already mark as completed");
        }
    }

    @Override
    public boolean isDialogCompleted() {
        return this.dialogCompleted.get();
    }

    @Override
    public DialogContext clone() {
        return new DialogContextImpl(
                objectMap != null ? new HashMap<>(objectMap) : null,
                getState(), isDialogCompleted()
        );
    }
}
