package ru.daniil4jk.strongram.longpolling;

import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import ru.daniil4jk.strongram.core.bot.TelegramClientProvider;

public interface HasBot extends LongPollingUpdateConsumer, TelegramClientProvider, TokenProvider {
}
