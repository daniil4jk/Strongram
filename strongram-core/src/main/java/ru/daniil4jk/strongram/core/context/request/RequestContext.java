package ru.daniil4jk.strongram.core.context.request;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.core.bot.Bot;
import ru.daniil4jk.strongram.core.context.storage.Storage;
import ru.daniil4jk.strongram.core.unboxer.Unboxer;

import java.util.List;

public interface RequestContext {
    List<BotApiMethod<?>> getResponses();
    void respond(BotApiMethod<?> response);
    void respond(String text);

    TelegramUUID getUserId();
    Update getRequest();
    <T> T getRequest(Unboxer<T> unboxer);

    Storage getStorage();
    Bot getBot();
}
