package ru.daniil4jk.strongram.longpolling.adapter.interfaces;

import ru.daniil4jk.strongram.core.response.client.provider.TelegramClientProvider;

public interface HasLongPollingBot extends
        LongPollingSingleUpdateConsumer,
        TelegramClientProvider,
        TokenProvider {
}
