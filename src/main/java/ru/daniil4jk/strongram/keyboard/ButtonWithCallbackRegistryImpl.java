package ru.daniil4jk.strongram.keyboard;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ButtonWithCallbackRegistryImpl implements ButtonWithCallbackRegistry {
    private final Map<String, ButtonCallbackAction> map = new ConcurrentHashMap<>();
    private final ButtonAutoRemover remover = new ButtonAutoRemover(map::remove);

    @Override
    public boolean add(ButtonWithCallback button, boolean temporarily) {
        Objects.requireNonNull(button.getCallbackId(), "callbackId can`t be null");
        Objects.requireNonNull(button.getCallbackAction(), "buttonCallbackAction can`t be null");

        button.prepareToAddingInRegistry();

        try {
            map.compute(button.getCallbackId(), (cd, b) -> {
                if (b == null) {
                    if (temporarily) {
                        remover.add(button.getCallbackId());
                    }
                    return button.getCallbackAction();
                }

                if (b.equals(button.getCallbackAction())) {
                    return b;
                } else {
                    throw new IllegalStateException("with key %s already associated element %s"
                            .formatted(cd, b));
                }
            });
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    @Override
    public boolean contains(String callbackData) {
        return map.containsKey(callbackData);
    }

    @Override
    public ButtonCallbackAction get(String callbackData) {
        return map.get(callbackData);
    }

    @Override
    public boolean remove(ButtonWithCallback button) {
        return remove(button.getCallbackId(), button.getCallbackAction());
    }

    @Override
    public boolean remove(String callbackId, ButtonCallbackAction action) {
        Objects.requireNonNull(callbackId, "callbackId can`t be null");
        Objects.requireNonNull(callbackId, "buttonCallbackAction can`t be null");
        try {
            map.compute(callbackId, (cd, b) -> {
                if (b == null) {
                    throw new IllegalStateException("with key %s not associated element"
                            .formatted(cd));
                }
                if (!b.equals(action)) {
                    throw new IllegalStateException("with key %s associated element %s, but not %s"
                            .formatted(cd, b, action));
                }
                return null;
            });
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }
}
