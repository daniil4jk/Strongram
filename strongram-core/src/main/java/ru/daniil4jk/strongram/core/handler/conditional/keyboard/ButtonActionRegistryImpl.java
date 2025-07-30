package ru.daniil4jk.strongram.core.handler.conditional.keyboard;

import java.util.HashMap;
import java.util.Map;

public class ButtonActionRegistryImpl<Button> implements ButtonActionRegistry<Button> {
    private final Map<Button, KeyboardUpdateHandler.ButtonAction> map = new HashMap<>();

    @Override
    public boolean addAction(Button button, KeyboardUpdateHandler.ButtonAction action) {
        try {
            map.compute(button, (b, a) -> {
                if (a == null) return action;
                throw new IllegalArgumentException();
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean exists(Button button) {
        return map.containsKey(button);
    }

    @Override
    public KeyboardUpdateHandler.ButtonAction getAction(Button button) {
        return map.get(button);
    }
}
