package ru.daniil4jk.strongram.longpolling.adapter.interfaces;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface LongPollingSingleUpdateConsumer extends LongPollingUpdateConsumer {
    void consumeSingle(Update update);

    @Override
    default void consume(@NotNull List<Update> updates) {
        updates.forEach(this::consumeSingle);
    }
}
