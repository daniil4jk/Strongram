package ru.daniil4jk.strongram.spring.longpolling;

import lombok.Getter;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import ru.daniil4jk.strongram.core.Bot;
import ru.daniil4jk.strongram.longpolling.LongPollingBotWrapper;

@Getter
public class SpringLongPollingBotAdapter implements SpringLongPollingBot {
    private final String botToken;
    private final LongPollingBotWrapper updatesConsumer;

    public SpringLongPollingBotAdapter(Bot bot) {
        this.botToken = bot.getCredentials().getBotToken();
        this.updatesConsumer = new LongPollingBotWrapper(bot);
    }
}
