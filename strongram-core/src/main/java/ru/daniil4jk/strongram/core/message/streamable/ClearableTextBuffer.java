package ru.daniil4jk.strongram.core.message.streamable;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClearableTextBuffer {
    private final Queue<String> incomingParts = new ConcurrentLinkedQueue<>();

    public void add(String part) {
        incomingParts.add(part);
    }

    public String getAndClear() {
        String part = incomingParts.poll();
        if (part == null) {
            return null;
        }

        StringBuilder diff = new StringBuilder();
        while (part != null) {
            diff.append(part);
            part = incomingParts.poll();
        }

        return diff.toString();
    }

    public boolean isEmpty() {
        return incomingParts.isEmpty();
    }
}
