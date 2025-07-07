package ru.daniil4jk.strongram.keyboard;

import java.util.HashMap;
import java.util.Map;

public class ButtonWithCallbackRegistryImpl implements ButtonWithCallbackRegistry {
    private final Map<String, ButtonWithCallback> map = new HashMap<>();

    @Override
    public boolean add(ButtonWithCallback button) {
        try {
            map.computeIfPresent(button.getCallbackId(), (cd, b) -> {
                if (b.equals(button)) {
                    throw new IllegalStateException("element %s already added"
                            .formatted(b));
                } else {
                    throw new IllegalStateException("with key %s already associated element %s"
                            .formatted(cd, b));
                }
            });
            map.put(button.getCallbackId(), button);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean contains(String callbackData) {
        return map.containsKey(callbackData);
    }

    @Override
    public ButtonWithCallback get(String callbackData) {
        return map.get(callbackData);
    }

    @Override
    public boolean remove(ButtonWithCallback button) {
        try {
            map.computeIfAbsent(button.getCallbackId(), cd -> {
                throw new IllegalStateException("with key %s not associated element"
                        .formatted(cd));
            });
            map.computeIfPresent(button.getCallbackId(), (cd, b) -> {
                if (!b.equals(button)) {
                    throw new IllegalStateException("with key %s associated element %s, but not %s"
                            .formatted(cd, b, button));
                }
                return null;
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
