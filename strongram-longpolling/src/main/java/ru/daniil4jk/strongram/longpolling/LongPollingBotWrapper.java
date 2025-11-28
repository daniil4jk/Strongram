package ru.daniil4jk.strongram.longpolling;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.core.Bot;
import ru.daniil4jk.strongram.core.TelegramClientProvider;

import java.util.List;

@Slf4j
public class LongPollingBotWrapper implements LongPollingUpdateConsumer {
    private final Bot bot;
    private volatile TelegramClient client;

    public LongPollingBotWrapper(Bot bot) {
        this.bot = bot;
        if (bot instanceof TelegramClientProvider provider) {
            if (provider.clientRequired()) {
                this.client = new OkHttpTelegramClient(bot.getCredentials().getBotToken());
                if (provider.clientRequired()) {
                    provider.setClient(client);
                }
            }
        }
    }

    @Override
    public void consume(@NotNull List<Update> updates) {
        updates.forEach(this::consumeSingle);
    }

    public void consumeSingle(Update update) {
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
