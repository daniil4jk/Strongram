package ru.daniil4jk.strongram.longpolling;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.core.Bot;
import ru.daniil4jk.strongram.core.TelegramClientProvider;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
public class LongPollingBotWrapper implements LongPollingUpdateConsumer, Consumer<Update> {
    private final Bot bot;
    private volatile TelegramClient client;

    public LongPollingBotWrapper(Bot bot) {
        this.bot = bot;
        if (bot instanceof TelegramClientProvider provider) {
            var client = provider.getClient();
            if (client == null) {
                this.client = client = new OkHttpTelegramClient(bot.getCredentials().getBotToken());
                if (provider.canSetClient()) {
                    provider.setClientOnce(client);
                }
            }
        }
    }

    @Override
    public void consume(@NotNull List<Update> updates) {
        updates.forEach(this);
    }

    @Override
    public void accept(Update update) {
        try {
            var method = bot.process(update);
            if (method != null) {
                client.execute(method);
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }
}
