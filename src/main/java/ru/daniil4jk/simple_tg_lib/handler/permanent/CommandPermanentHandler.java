package ru.daniil4jk.simple_tg_lib.handler.permanent;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Collection;

public class CommandPermanentHandler extends AbstractPermanentHandler {
    private final Collection<BotCommand> commands;

    public CommandPermanentHandler(Collection<BotCommand> commands) {
        this.commands = commands;
    }

    @Override
    public void process(AbsSender absSender, Update update) {
        if (update.hasMessage() && update.getMessage().isCommand()) {
            processCommand(update.getMessage());
        }
        processNext(update);
    }

    private void processCommand(Message message) {
        //todo realize
    }
}
