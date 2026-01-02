package ru.daniil4jk.strongram.longpolling.response;

import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.core.response.entity.Response;
import ru.daniil4jk.strongram.core.response.sender.accumulating.ManagedAccumulatingSender;

public class LongPollingSender extends ManagedAccumulatingSender {
    public void sendAll(TelegramClient client) {
        for (Response<?> response : getQueuedMessages()) {
            response.sendUsing(client);
        }
    }
}
