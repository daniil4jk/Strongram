package ru.daniil4jk.strongram.core;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public final class BotCredentials {
    private final String botToken;
    private String botName;

    public BotCredentials(String botToken, String botName) {
        this.botToken = botToken;
        this.botName = botName;
    }

    @Override
    public String toString() {
        return "BotCredentials{" +
                "botToken='SECRET-INFO'" +
                ", botName='" + botName + '\'' +
                '}';
    }
}
