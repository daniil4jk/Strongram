package ru.daniil4jk.strongram.core.context.request;

import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.daniil4jk.strongram.core.bot.Bot;
import ru.daniil4jk.strongram.core.context.storage.Storage;
import ru.daniil4jk.strongram.core.unboxer.Unboxer;

import java.io.File;
import java.util.List;

public interface RequestContext {
    List<PartialBotApiMethod<?>> getResponses();

    void respond(PartialBotApiMethod<?> response);
    void respond(String text);
    void respond(String text, ReplyKeyboard keyboard);
    void respond(String text, File file, MediaType type);

    TelegramUUID getUUID();
    Update getRequest();
    <T> T getRequest(Unboxer<T> unboxer);

    Storage getStorage();
    Bot getBot();

    enum MediaType {
        Photo, Video, Audio, Document
    }
}