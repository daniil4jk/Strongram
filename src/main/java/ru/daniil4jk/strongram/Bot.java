package ru.daniil4jk.strongram;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Bot {
    BotApiMethod<?> process(Update update);
    BotCredentials getCredentials();
}
