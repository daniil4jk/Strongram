package ru.daniil4jk.strongram.longpolling;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.core.bot.Bot;
import ru.daniil4jk.strongram.core.bot.TelegramClientProvider;
import ru.daniil4jk.strongram.longpolling.response.LongPollingSender;

import java.util.List;

@Slf4j
public class LongPollingBotAdapter implements HasBot, TelegramClientProvider {
    @Getter
    private final String token;
    private volatile TelegramClient client;
    private final Bot bot;

    public LongPollingBotAdapter(String token, Bot bot) {
        this.token = token;
        this.bot = bot;
    }

    @Override
    public void consume(@NotNull List<Update> updates) {
        updates.forEach(this::consumeSingle);
    }

    public void consumeSingle(Update update) {
        try (LongPollingSender responder = new LongPollingSender()) {
            bot.accept(update, responder);
            responder.sendAll(client);
        } catch (Exception e) {
            log.error("Error occurred while bot processing update", e);
        }
    }

    @Override
    public TelegramClient getClient() {
        return client;
    }

    @Override
    public void setClient(TelegramClient client) {
        this.client = client;
    }

    @Override
    public boolean hasClient() {
        return client != null;
    }
}
