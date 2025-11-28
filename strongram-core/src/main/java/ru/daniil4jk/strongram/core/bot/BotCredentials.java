package ru.daniil4jk.strongram.core.bot;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public final class BotCredentials {
    @ToString.Exclude
    private final String token;
    private String name;

    public BotCredentials(String token, String name) {
        this.token = token;
        this.name = name;
    }
}
