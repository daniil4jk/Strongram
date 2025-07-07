package ru.daniil4jk.strongram.command;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import ru.daniil4jk.strongram.TelegramUUID;
import ru.daniil4jk.strongram.context.BotContext;


public interface BotCommand {
    String getCommandIdentifier();

    String getDescription();

    BotApiMethod<?> process(TelegramUUID telegramUUID, BotContext context, String[] arguments);
}