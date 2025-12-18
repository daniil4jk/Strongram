package ru.daniil4jk.strongram.core.responder;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import ru.daniil4jk.strongram.core.bot.TelegramClientProvider;

import java.util.List;

public class SelectiveResponder implements Responder {
    public static final int MAX_MESSAGES_PER_SECOND = 30;
    private final BatchResponder batchResponder;
    private final SimpleResponder simpleResponder;

    public SelectiveResponder(TelegramClientProvider provider) {
        batchResponder = new BatchResponder(provider);
        simpleResponder = new SimpleResponder(provider);
    }

    @Override
    public void send(@NotNull List<BotApiMethod<?>> messages) {
        if (messages.size() >= MAX_MESSAGES_PER_SECOND) {
            batchResponder.send(messages);
        } else {
            simpleResponder.send(messages);
        }
    }
}
