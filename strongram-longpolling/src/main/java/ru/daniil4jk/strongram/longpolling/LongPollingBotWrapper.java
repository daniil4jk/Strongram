package ru.daniil4jk.strongram.longpolling;

import lombok.extern.slf4j.Slf4j;
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
public class LongPollingBotWrapper implements LongPollingUpdateConsumer, Consumer<Update>, TelegramClientProvider {
    private final Bot bot;
    private volatile TelegramClient client;

    public LongPollingBotWrapper(Bot bot) {
        this.bot = bot;
        if (bot instanceof TelegramClientProvider provider) {
            client = provider.getClient();
        }
    }

    @Override
    public void accept(Update update) {
        try {
            var method = bot.process(update);
            if (method != null) {
                getClient().execute(method);
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public TelegramClient getClient() {
        if (client == null) {
            synchronized (this) {
                if (client == null) {
                    client = new OkHttpTelegramClient(bot.getCredentials().getBotToken());
                }
            }
        }
        return client;
    }

    @Override
    public void consume(List<Update> updates) {
        updates.forEach(this);
    }
}
