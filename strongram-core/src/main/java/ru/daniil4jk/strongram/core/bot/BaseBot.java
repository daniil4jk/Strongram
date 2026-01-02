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
    @Getter
    private final String username;

    public BaseBot(String username) {
        this.username = username;
    }
}