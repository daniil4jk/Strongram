package ru.daniil4jk.strongram.core.dto;

import lombok.*;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    @Singular
    private volatile List<BotApiMethod<?>> immediatelyMethods;
    @Singular
    private volatile List<SendBotApiMethodDelayedTask> delayedMethods;

    private void initImmediatelyMethods() {
        if (immediatelyMethods == null) {
            synchronized (this) {
                if (immediatelyMethods == null) {
                    immediatelyMethods = new ArrayList<>();
                }
            }
        }
    }

    private void initDelayedMethods() {
        if (delayedMethods == null) {
            synchronized (this) {
                if (delayedMethods == null) {
                    delayedMethods = new ArrayList<>();
                }
            }
        }
    }

    public boolean add(BotApiMethod<?> method) {
        initImmediatelyMethods();
        return immediatelyMethods.add(method);
    }

    public boolean add(SendBotApiMethodDelayedTask task) {
        initDelayedMethods();
        return delayedMethods.add(task);
    }


    public static Response of(BotApiMethod<?> ... methods) {
        return new Response(List.of(methods), null);
    }

    public static Response of(SendBotApiMethodDelayedTask ... tasks) {
        return new Response(null, List.of(tasks));
    }
}
