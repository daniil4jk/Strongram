package ru.daniil4jk.strongram.core.chain.context;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.core.bot.BotCredentials;
import ru.daniil4jk.strongram.core.chain.caster.Transformer;
import ru.daniil4jk.strongram.core.dto.TelegramUUID;

import java.util.List;

public interface Context {
    List<BotApiMethod<?>> getResponses();
    void respond(BotApiMethod<?> response);
    void respond(String text);

    TelegramUUID getUserId();
    Update getRequest();
    <T> T getRequest(Transformer<T> transformer);

    RequestState getState();
    TelegramClient getClient();
    BotCredentials getCredentials();
}
