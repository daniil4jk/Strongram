package ru.daniil4jk.strongram.core.chain.context;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.core.bot.BotCredentials;
import ru.daniil4jk.strongram.core.chain.caster.Caster;
import ru.daniil4jk.strongram.core.dto.TelegramUUID;

import java.util.List;

public interface Context {
    List<BotApiMethod<?>> getResponsesList();
    void respond(BotApiMethod<?> response);
    void respond(String text);

    TelegramUUID getUserId();
    Update getRequest();
    <T> T getRequestAs(Caster<T> caster);

    RequestState getRequestState();
    TelegramClient getClient();
    BotCredentials getCredentials();
}
