package ru.daniil4jk.strongram.core.chain.handler.preinstalled.exreport;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ExceptionFormatters {
    @Contract(value = " -> new", pure = true)
    public static @NotNull ExceptionFormatter info() {
        return new InfoExceptionFormatter();
    }

    @Contract(value = " -> new", pure = true)
    public static @NotNull ExceptionFormatter debug() {
        return new DebugExceptionFormatter();
    }
}
