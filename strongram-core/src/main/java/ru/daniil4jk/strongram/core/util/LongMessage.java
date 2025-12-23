package ru.daniil4jk.strongram.core.util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LongMessage {
    public static final String WORD_SEPARATOR = " ";
    public static final int MAX_MSG_SIZE = 4096;

    private List<String> messages;
    private StringBuilder currentMessage = new StringBuilder();;

    public LongMessage(String originalMessage) {
        split(originalMessage);
    }

    private void split(@NotNull String originalMessage) {
        if (originalMessage.length() <= MAX_MSG_SIZE) {
            messages = List.of(originalMessage);
            return;
        }

        messages = new ArrayList<>();
        String[] words = originalMessage.split(WORD_SEPARATOR);

        for (String word : words) {
            if (word.length() > MAX_MSG_SIZE) {
                for (int i = 0; i < word.length(); i += MAX_MSG_SIZE) {
                    exchangeBuilders();
                    currentMessage.append(
                            word,
                            i,
                            Math.min(word.length(), i + MAX_MSG_SIZE)
                    );
                }
            } else {
                if (currentMessage.length() + word.length() + WORD_SEPARATOR.length() >= MAX_MSG_SIZE) {
                    exchangeBuilders();
                }
                currentMessage.append(WORD_SEPARATOR).append(word);
            }
        }
        exchangeBuilders();
    }

    private void exchangeBuilders() {
        messages.add(currentMessage.toString().trim());
        currentMessage = new StringBuilder();
    }

    public List<String> asLegalLengthMessageList() {
        return messages;
    }
}