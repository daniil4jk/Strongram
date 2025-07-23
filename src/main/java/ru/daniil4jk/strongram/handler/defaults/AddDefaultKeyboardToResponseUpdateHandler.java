package ru.daniil4jk.strongram.handler.defaults;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.daniil4jk.strongram.context.BotContext;
import ru.daniil4jk.strongram.handler.AbstractUpdateHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Setter
public class AddDefaultKeyboardToResponseUpdateHandler extends AbstractUpdateHandler {
    private Function<BotApiMethod<?>, ReplyKeyboard> defaultKeyboardFactory;

    public void setDefaultKeyboard(ReplyKeyboard keyboard) {
        this.defaultKeyboardFactory = method -> keyboard;
    }

    @Override
    protected void afterExecute(Update update, BotContext context, @Nullable BotApiMethod<?> result) {
        if (result == null || defaultKeyboardFactory == null) return;

        Optional<Method> getReplyMarkupMethod = getGetReplyMarkupMethod(result);
        if (getReplyMarkupMethod.isEmpty()) return;

        ReplyKeyboard currentKeyboard = getReplyMarkup(result, getReplyMarkupMethod.get());
        if (currentKeyboard != null) return;

        var defaultKeyboard = defaultKeyboardFactory.apply(result);
        if (defaultKeyboard == null) return;

        Optional<Method> setReplyKeyboardMethod = getSetReplyKeyboardMethod(result);
        if (setReplyKeyboardMethod.isEmpty()) return;

        setReplyMarkup(result, setReplyKeyboardMethod.get(), defaultKeyboard);
    }

    @NotNull
    private Optional<Method> getGetReplyMarkupMethod(@NotNull BotApiMethod<?> instance) {
        StringBuilder methods = new StringBuilder();
        for (Method method : instance.getClass().getMethods()) {
            methods.append(method.getReturnType().getSimpleName()).append("\n");
        }
        //log.debug("BotApiMethod method return values: {}", methods);
        return Arrays.stream(instance.getClass().getDeclaredMethods())
                .filter(method -> method.getReturnType().equals(ReplyKeyboard.class))
                .findAny();
    }

    private ReplyKeyboard getReplyMarkup(BotApiMethod<?> instance, Method getReplyMarkupMethod) {
        try {
            return (ReplyKeyboard) getReplyMarkupMethod.invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    private Optional<Method> getSetReplyKeyboardMethod(@NotNull BotApiMethod<?> result) {
        return Arrays.stream(result.getClass().getMethods())
                .filter(method -> ArrayUtils.contains(method.getParameterTypes(), ReplyKeyboard.class))
                .findAny();
    }

    private void setReplyMarkup(BotApiMethod<?> instance, Method setReplyKeyboardMethod, ReplyKeyboard keyboard) {
        try {
            setReplyKeyboardMethod.invoke(instance, keyboard);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
