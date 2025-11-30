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
    private String username;

    public BotCredentials(String token, String username) {
        this.token = token;
        this.username = username;
    }
}
