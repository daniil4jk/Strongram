package ru.daniil4jk.strongram.core.chain.context;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.core.bot.Bot;
import ru.daniil4jk.strongram.core.chain.caster.Transformer;
import ru.daniil4jk.strongram.core.storage.Storage;

import java.util.List;

public interface RequestContext {
    List<BotApiMethod<?>> getResponses();
    void respond(BotApiMethod<?> response);
    void respond(String text);

    TelegramUUID getUserId();
    Update getRequest();
    <T> T getRequest(Transformer<T> transformer);

    Storage getStorage();
    Bot getBot();
}
