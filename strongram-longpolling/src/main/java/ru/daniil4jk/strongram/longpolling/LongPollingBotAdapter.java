package ru.daniil4jk.strongram.longpolling;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.core.bot.Bot;
import ru.daniil4jk.strongram.core.bot.TelegramClientProvider;

import java.util.List;

@Slf4j
public class LongPollingBotAdapter implements HasBot {
    @Getter
    private final String token;
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
        try {
            List<BotApiMethod<?>> responses = bot.apply(update);
            responses.forEach(this::sendResponse);
        } catch (Exception e) {
            log.error("Error occurred while bot processing update", e);
        }
    }

    private void sendResponse(BotApiMethod<?> response) {
        try {
            bot.getClient().execute(response);
        } catch (TelegramApiException e) {
            log.warn("Sending response failed", e);
        }
    }

    @Override
    public TelegramClient getClient() {
        return bot.getClient();
    }

    @Override
    public boolean hasClient() {
        return bot.hasClient();
    }

    @Override
    public void setClient(TelegramClient client) {
        bot.setClient(client);
    }
}
