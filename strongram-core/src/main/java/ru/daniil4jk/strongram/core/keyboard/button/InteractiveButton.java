package ru.daniil4jk.strongram.core.keyboard.button;

import ru.daniil4jk.strongram.core.context.request.RequestContext;

import java.util.function.Consumer;

public interface InteractiveButton extends Consumer<RequestContext> {
    String getCallbackData();

    static boolean isInstance(Object t) {
        return t instanceof InteractiveButton;
    }

    static InteractiveButton cast(Object t) {
        return (InteractiveButton) t;
    }
}
