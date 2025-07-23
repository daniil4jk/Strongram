package ru.daniil4jk.strongram.handler.conditional.keyboard.pattern;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class AbstractKeyboardPatternGenerator implements KeyboardPatternGenerator {
    private static final Pattern EMPTY_PATTERN = Pattern.compile("(?!)");

    private final Pattern pattern;

    public AbstractKeyboardPatternGenerator(Stream<String> buttonPayloads) {
        pattern = createPattern(buttonPayloads);
    }

    @NotNull
    private static Pattern createPattern(Stream<String> buttonPayloads) {
        if (buttonPayloads == null) {
            return EMPTY_PATTERN;
        }

        String regex = buttonPayloads
                .filter(Objects::nonNull)
                .map(Pattern::quote)
                .collect(Collectors.joining("|"));

        if (regex.trim().isEmpty()) {
            return EMPTY_PATTERN;
        }

        return Pattern.compile(regex);
    }
}
