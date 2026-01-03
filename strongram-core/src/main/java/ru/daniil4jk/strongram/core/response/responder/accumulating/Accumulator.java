package ru.daniil4jk.strongram.core.response.responder.accumulating;

import ru.daniil4jk.strongram.core.response.dto.Response;
import ru.daniil4jk.strongram.core.response.responder.Responder;

import java.util.List;

public interface Accumulator extends Responder {
    List<Response<?>> getQueuedMessages();
}
