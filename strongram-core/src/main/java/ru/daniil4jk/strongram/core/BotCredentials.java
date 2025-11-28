package ru.daniil4jk.strongram.core;

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
    private final String botToken;
    private String botName;

    public BotCredentials(String botToken, String botName) {
        this.botToken = botToken;
        this.botName = botName;
    }
}
