package ru.daniil4jk.strongram.longpolling.adapter;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.core.bot.Bot;
import ru.daniil4jk.strongram.core.response.client.provider.MutableTelegramClientProvider;
import ru.daniil4jk.strongram.core.response.client.provider.TelegramClientProvider;
import ru.daniil4jk.strongram.core.response.sender.Sender;
import ru.daniil4jk.strongram.core.util.DefaultExecutor;
import ru.daniil4jk.strongram.longpolling.adapter.interfaces.HasLongPollingBot;

import java.util.concurrent.ExecutorService;

@Slf4j
public class LongPollingBotAdapter implements HasLongPollingBot {

    private final TelegramClientProvider provider =
        new MutableTelegramClientProvider();

    @Getter
    private final String token;
    private final Bot bot;

    public LongPollingBotAdapter(String token, Bot bot) {
        this(token, DefaultExecutor.initOrGet(), bot);
    }

    public LongPollingBotAdapter(
        String token,
        ExecutorService sendExecutor,
        Bot bot
    ) {
        this.token = token;
        this.bot = bot;

        setBotCallback(sendExecutor);
    }

    private void setBotCallback(ExecutorService sendExecutor) {
        Sender sender = new Sender(sendExecutor, provider);
        bot.setDefaultCallback(sender::sendAllUsingClient);
    }

    public void consumeSingle(Update update) {
        try {
            bot.accept(update);
        } catch (Exception e) {
            log.error(
                "Error occurred while longpolling bot processing update",
                e
            );
        }
    }

    @Override
    public TelegramClient getClient() {
        return provider.getClient();
    }

    @Override
    public void setClient(TelegramClient client) {
        provider.setClient(client);
    }

    @Override
    public boolean hasClient() {
        return provider.hasClient();
    }
}
