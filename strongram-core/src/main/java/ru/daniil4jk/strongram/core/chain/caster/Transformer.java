package ru.daniil4jk.strongram.core.chain.caster;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Function;

public interface Transformer<O> extends Function<Update, O> {
    @Override
    @NotNull
    default <V> Transformer<V> andThen(@NotNull Function<? super O, ? extends V> after) {
        return update -> after.apply(apply(update));
    }
}
