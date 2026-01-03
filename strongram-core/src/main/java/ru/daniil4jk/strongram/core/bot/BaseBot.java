package ru.daniil4jk.strongram.core.bot;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@ToString
@EqualsAndHashCode
public abstract class BaseBot implements Bot {
    private static final String WHITESPACE = " ";
    private static final String DOG = "@";

    @Getter
    private final String username;

    public BaseBot(String username) {
        this.username = formatUsername(username);
    }

    private static String formatUsername(String raw) {
        raw = raw.trim().toLowerCase();
        if (raw.split(WHITESPACE).length > 1) {
            throw new IllegalArgumentException(
                    "Username %s contains whitespace".formatted(raw)
            );
        }
        if (!raw.startsWith(DOG)) {
            raw = DOG + raw;
        }
        return raw;
    }
}