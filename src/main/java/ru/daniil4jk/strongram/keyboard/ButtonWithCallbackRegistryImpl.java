package ru.daniil4jk.strongram.keyboard;

import ru.daniil4jk.strongram.TelegramUUID;

import java.util.HashMap;
import java.util.Map;

public class ButtonWithCallbackRegistryImpl implements ButtonWithCallbackRegistry {
    private final Map<TelegramUUID, Map<String, ButtonWithCallback>> map = new HashMap<>();

    private Map<String, ButtonWithCallback> getMapByUser(TelegramUUID uuid) {
        return map.computeIfAbsent(uuid, id -> new HashMap<>());
    }

    @Override
    public boolean add(TelegramUUID uuid, ButtonWithCallback button) {
        button.prepareToAddingInRegistry();
        try {
            getMapByUser(uuid).compute(button.getCallbackId(), (cd, b) -> {
                if (b == null) {
                    return button;
                }

                if (b.equals(button)) {
                    throw new IllegalStateException("element %s already added"
                            .formatted(b));
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
    public boolean contains(TelegramUUID uuid, String callbackData) {
        return getMapByUser(uuid).containsKey(callbackData);
    }

    @Override
    public ButtonWithCallback get(TelegramUUID uuid, String callbackData) {
        return getMapByUser(uuid).get(callbackData);
    }

    @Override
    public boolean remove(TelegramUUID uuid, ButtonWithCallback button) {
        try {
            getMapByUser(uuid).compute(button.getCallbackId(),
                    (cd, b) -> {
                if (b == null) {
                    throw new IllegalStateException("with key %s not associated element"
                            .formatted(cd));
                }
                if (!b.equals(button)) {
                    throw new IllegalStateException("with key %s associated element %s, but not %s"
                            .formatted(cd, b, button));
                }
                return null;
            });
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }
}
