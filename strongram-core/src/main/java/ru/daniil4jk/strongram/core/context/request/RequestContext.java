package ru.daniil4jk.strongram.core.context.request;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.core.context.storage.Storage;
import ru.daniil4jk.strongram.core.response.responder.smart.SmartResponder;
import ru.daniil4jk.strongram.core.unboxer.Unboxer;

public interface RequestContext {
    TelegramUUID getUUID();

    Update getRequest();

    <T> T getRequest(Unboxer<T> unboxer);

    Storage getRequestScopeStorage();

    String getBotUsername();

    SmartResponder getResponder();
}