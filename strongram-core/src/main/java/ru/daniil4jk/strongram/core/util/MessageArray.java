package ru.daniil4jk.strongram.core.util;

import java.util.ArrayList;
import java.util.List;

public final class MessageArray {
    public static final String SPLIT_SYMBOL = " ";
    public static final int MAX_MSG_SIZE = 4096;

    private final List<String> messages = new ArrayList<>();

    public MessageArray(String originalMessage) {
        split(originalMessage);
    }

    private void split(String originalMessage) {
        if (originalMessage.length() <= MAX_MSG_SIZE) {
            messages.add(originalMessage);
            return;
        }

        String[] words = originalMessage.split(SPLIT_SYMBOL);

        StringBuilder currentMessage = new StringBuilder();
        for (String word : words) {
            if (currentMessage.length() + word.length() >= MAX_MSG_SIZE) {
                messages.add(currentMessage.toString().trim());
                currentMessage = new StringBuilder();
            }

            currentMessage.append(" ").append(word);
        }
        messages.add(currentMessage.toString().trim());
    }

    public List<String> asList() {
        return messages;
    }
}
