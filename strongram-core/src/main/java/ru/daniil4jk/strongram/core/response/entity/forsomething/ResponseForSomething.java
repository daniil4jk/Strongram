package ru.daniil4jk.strongram.core.response.entity.forsomething;

import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import ru.daniil4jk.strongram.core.response.entity.Response;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

public interface ResponseForSomething<Method extends PartialBotApiMethod<?>, Object extends Serializable>
        extends Response<Method> {
    CompletableFuture<Object> getObject();
}
