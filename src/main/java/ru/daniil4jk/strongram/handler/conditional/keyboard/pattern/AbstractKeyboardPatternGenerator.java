package ru.daniil4jk.strongram.handler.conditional.keyboard.pattern;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Getter
public class AbstractKeyboardPatternGenerator implements KeyboardPatternGenerator {
    private final Pattern pattern;

    public AbstractKeyboardPatternGenerator(Collection<String> buttonPayloads) {
        pattern = createPattern(buttonPayloads);
    }

    @NotNull
    private static Pattern createPattern(Collection<String> buttonPayloads) {
        if (buttonPayloads == null || buttonPayloads.isEmpty()) {
            return Pattern.compile("(?!)");
        }

        String regex = buttonPayloads.stream()
                .filter(Objects::nonNull)
                .map(Pattern::quote)
                .collect(Collectors.joining("|"));
        return Pattern.compile(regex);
    }
}
