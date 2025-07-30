package ru.daniil4jk.strongram.spring.longpolling;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import ru.daniil4jk.strongram.core.Bot;
import ru.daniil4jk.strongram.longpolling.LongPollingBotWrapper;
import ru.daniil4jk.strongram.longpolling.MultithreadingLongPollingBotWrapper;

import java.util.concurrent.ExecutorService;

@Getter
@RequiredArgsConstructor
public class SpringLongPollingBotAdapter implements SpringLongPollingBot {
    private final String botToken;
    private final LongPollingUpdateConsumer updatesConsumer;

    public static SpringLongPollingBotAdapter create(Bot bot) {
        return new SpringLongPollingBotAdapter(
                bot.getCredentials().getBotToken(),
                new LongPollingBotWrapper(bot)
        );
    }

    public static SpringLongPollingBotAdapter create(Bot bot, ExecutorService executor) {
        LongPollingUpdateConsumer consumer = new LongPollingBotWrapper(bot);
        consumer = new MultithreadingLongPollingBotWrapper(consumer, executor);

        return new SpringLongPollingBotAdapter(bot.getCredentials().getBotToken(), consumer);
    }

}
