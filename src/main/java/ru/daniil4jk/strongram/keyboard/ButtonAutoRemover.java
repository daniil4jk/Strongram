package ru.daniil4jk.strongram.keyboard;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class ButtonAutoRemover {
    private final Map<String, AtomicLong> buttonIdsByTimeOfLastUse = new ConcurrentHashMap<>();
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final Consumer<String> remove;

    public ButtonAutoRemover(Consumer<String> remove) {
        this.remove = remove;
        setupTimer();
    }

    private void setupTimer(){
        executor.scheduleAtFixedRate(
                () -> {
                    long currentTime = System.currentTimeMillis();
                    for (var iterator = buttonIdsByTimeOfLastUse.entrySet().iterator(); iterator.hasNext(); ) {
                        var entry = iterator.next();
                        if (currentTime - entry.getValue().get() > TimeUnit.HOURS.toMillis(24)) {
                            remove.accept(entry.getKey());
                            iterator.remove();
                        }
                    }
                }, 1, 1, TimeUnit.MINUTES
        );
    }

    public void add(String buttonId) {
        AtomicLong addTime = new AtomicLong(System.currentTimeMillis());
        buttonIdsByTimeOfLastUse.put(buttonId, addTime);
    }
}
