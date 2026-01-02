package ru.daniil4jk.strongram.core.response.sender.accumulating;

import ru.daniil4jk.strongram.core.response.dto.Response;
import ru.daniil4jk.strongram.core.response.sender.Sender;

import java.util.List;

public interface Accumulating extends Sender {
    List<Response<?>> getQueuedMessages();
}
