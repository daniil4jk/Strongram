package ru.daniil4jk.strongram.core.chain.handler.preinstalled;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.daniil4jk.strongram.core.chain.handler.BaseHandler;
import ru.daniil4jk.strongram.core.context.request.RequestContext;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.*;

@Slf4j
public class AddDefaultKeyboardHandler extends BaseHandler {
    public static final Class<ReplyKeyboard> REPLY_KEYBOARD_CLASS = ReplyKeyboard.class;

    private static final MethodHandles.Lookup lookup = MethodHandles.lookup();
    private static final Map<Class<?>, Methods> methodsByClass = new HashMap<>();
    private static final Set<Class<?>> exclude = new HashSet<>();

    @Setter
    private ReplyKeyboard defaultKeyboard;

    public AddDefaultKeyboardHandler(ReplyKeyboard defaultKeyboard) {
        this.defaultKeyboard = defaultKeyboard;
    }

    @Override
    protected final void process(RequestContext ctx) {
        processNext(ctx);

        for (BotApiMethod<?> msg : ctx.getResponses()) {
            Class<?> key = msg.getClass();

            if (exclude.contains(key)) {
                continue;
            }

            if (!methodsByClass.containsKey(key)) {
                tryFill(key);
                if (exclude.contains(key)) {
                    continue;
                }
            }

            Methods methods = methodsByClass.get(key);
            try {
                if (methods.get.invoke(msg) == null) {
                    methods.set.invoke(msg, defaultKeyboard);
                }
            } catch (Throwable e) {
                log.warn("Cannot set default keyboard", e);
            }
        }
    }

    private void tryFill(Class<?> key) {
        try {
            Methods methods = new Methods(
                    findGetMethod(key),
                    findSetMethod(key)
            );
            methodsByClass.put(key, methods);
        } catch (Exception e) {
            exclude.add(key);
        }
    }

    private MethodHandle findGetMethod(Class<?> key) {
        return Arrays.stream(key.getMethods())
                .filter(method -> REPLY_KEYBOARD_CLASS.isAssignableFrom(method.getReturnType()))
                .filter(method -> method.getName().contains("asList"))
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

    private MethodHandle findSetMethod(Class<?> key) {
        return Arrays.stream(key.getMethods())
                .filter(method -> method.getParameterCount() == 1)
                .filter(method ->
                        Arrays.stream(method.getParameterTypes())
                                .anyMatch(REPLY_KEYBOARD_CLASS::isAssignableFrom)
                )
                .filter(method -> method.getName().contains("set"))
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
            MethodHandle get,
            MethodHandle set
    ) {}
}
