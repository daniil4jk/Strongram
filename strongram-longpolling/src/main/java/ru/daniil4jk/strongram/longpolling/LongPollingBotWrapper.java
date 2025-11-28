package ru.daniil4jk.strongram.longpolling;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.core.bot.Bot;
import ru.daniil4jk.strongram.core.bot.BotCredentials;
import ru.daniil4jk.strongram.core.bot.CredentialsProvider;
import ru.daniil4jk.strongram.core.bot.TelegramClientProvider;

import java.util.List;

@Slf4j
public class LongPollingBotWrapper implements LongPollingUpdateConsumer, CredentialsProvider, TelegramClientProvider {
    private final Bot bot;

    public LongPollingBotWrapper(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void consume(@NotNull List<Update> updates) {
        updates.forEach(this::consumeSingle);
    }

    public void consumeSingle(Update update) {
        List<BotApiMethod<?>> responses = bot.apply(update);
       responses.forEach(this::sendResponse);
    }

    private void sendResponse(BotApiMethod<?> response) {
        try {
            bot.getClient().execute(response);
        } catch (TelegramApiException e) {
            log.warn("Sending response failed", e);
        }
    }

    @Override
    public BotCredentials getCredentials() {
        return bot.getCredentials();
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
