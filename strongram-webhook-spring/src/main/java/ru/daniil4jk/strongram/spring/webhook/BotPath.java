package ru.daniil4jk.strongram.spring.webhook;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface BotPath {
    String path();
}
