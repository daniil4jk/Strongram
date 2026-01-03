package ru.daniil4jk.strongram.core.util;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DefaultExecutor {
    private static final Lazy<ScheduledExecutorService> DEFAULT_EXECUTOR = new Lazy<>(
            DefaultExecutor::createScheduledCashedThreadPool
    );

    private static @NotNull ScheduledThreadPoolExecutor createScheduledCashedThreadPool() {
        var exec = new ScheduledThreadPoolExecutor(1);
        exec.setKeepAliveTime(60, TimeUnit.SECONDS);
        return exec;
    }

    public static ScheduledExecutorService initOrGet() {
        return DEFAULT_EXECUTOR.initOrGet();
    }

    public static boolean isInitialized() {
        return DEFAULT_EXECUTOR.isInitialized();
    }

    public static ScheduledExecutorService get() {
        return DEFAULT_EXECUTOR.get();
    }
}
