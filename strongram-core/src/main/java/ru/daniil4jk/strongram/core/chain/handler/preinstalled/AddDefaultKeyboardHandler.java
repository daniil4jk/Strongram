package ru.daniil4jk.strongram.core.chain.handler.preinstalled;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.daniil4jk.strongram.core.chain.handler.BaseHandler;
import ru.daniil4jk.strongram.core.context.request.RequestContext;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public abstract class AddDefaultKeyboardHandler extends BaseHandler {
    public static final Class<ReplyKeyboard> REPLY_KEYBOARD_CLASS = ReplyKeyboard.class;
    private static final String GET_METHOD_NAME_PART = "get";
    private static final String SET_METHOD_NAME_PART = "set";

    private static final MethodHandles.Lookup lookup = MethodHandles.lookup();
    private static final Map<Class<?>, Methods> methodsByClass = new HashMap<>();

    protected abstract ReplyKeyboard getDefaultKeyboard(RequestContext ctx);

    @Override
    protected final void process(RequestContext ctx) {
        processNext(ctx);

        for (BotApiMethod<?> msg : ctx.getResponses()) {
            Class<?> key = msg.getClass();

            if (!methodsByClass.containsKey(key)) {
                tryFill(key);
            }

            Methods methods = methodsByClass.get(key);

            if (!methods.invokable) {
                continue;
            }

            try {
                if (methods.get.invoke(msg) == null) {
                    methods.set.invoke(msg, getDefaultKeyboard(ctx));
                }
            } catch (Throwable e) {
                log.warn("Cannot set default keyboard", e);
            }
        }
    }

    private void tryFill(Class<?> key) {
        Methods methods;
        try {
            methods = new Methods(
                    true,
                    findGetMethod(key),
                    findSetMethod(key)
            );
        } catch (Exception e) {
            methods = new Methods(
                    false,
                    null,
                    null
            );
        }
        methodsByClass.put(key, methods);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private @NotNull MethodHandle findGetMethod(@NotNull Class<?> key) {
        return Arrays.stream(key.getMethods())
                .filter(method -> REPLY_KEYBOARD_CLASS.isAssignableFrom(method.getReturnType()))
                .filter(method -> method.getName().contains(GET_METHOD_NAME_PART))
                .map(Optional::of)
                .map(optional -> {
                    try {
                        return Optional.of(lookup.unreflect(optional.get()));
                    } catch (Exception e) {
                        return Optional.<MethodHandle>empty();
                    }
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findAny()
                .orElseThrow();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private @NotNull MethodHandle findSetMethod(@NotNull Class<?> key) {
        return Arrays.stream(key.getMethods())
                .filter(method -> method.getParameterCount() == 1)
                .filter(method ->
                        Arrays.stream(method.getParameterTypes())
                                .anyMatch(REPLY_KEYBOARD_CLASS::isAssignableFrom)
                )
                .filter(method -> method.getName().contains(SET_METHOD_NAME_PART))
                .map(Optional::of)
                .map(optional -> {
                    try {
                        return Optional.of(lookup.unreflect(optional.get()));
                    } catch (Exception e) {
                        return Optional.<MethodHandle>empty();
                    }
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findAny()
                .orElseThrow();
    }

    private record Methods(
            boolean invokable, //todo refactor to this
            MethodHandle get,
            MethodHandle set
    ) {}
}
