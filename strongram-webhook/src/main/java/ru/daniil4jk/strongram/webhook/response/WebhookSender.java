package ru.daniil4jk.strongram.webhook.response;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.core.response.entity.Response;
import ru.daniil4jk.strongram.core.response.sender.accumulating.ManagedAccumulatingSender;

public class WebhookSender extends ManagedAccumulatingSender {
    public BotApiMethod<?> sendAll(TelegramClient client) {
        if (responseCanBeReturned()) {
            return (BotApiMethod<?>) getQueuedMessages().get(0);
        }
        sendUsingClient(client);
        return null;
    }

    private boolean responseCanBeReturned() {
        if (getQueuedMessages().size() != 1) return false;
        var response = getQueuedMessages().get(0);
        return response.getEntry() instanceof BotApiMethod<?> &&
                !response.isObjectRequired();
    }

    private void sendUsingClient(TelegramClient client) {
        for (Response<?> entity : getQueuedMessages()) {
            entity.sendUsing(client);
        }
    }
}

