package ru.daniil4jk.simple_tg_lib.handler.permanent;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Collection;

/**
 * They form a Chain of Responsibility through which all
 * Updates that are not processed by user-specific handlers pass.
 */
public interface PermanentHandler {
    void setNext(PermanentHandler handler);
    void process(AbsSender absSender, Update update);

    //todo add Command handler and Don`tKnowWhatIsThis handler
    static PermanentHandler defaultPermanentHandlerChain(Collection<BotCommand> botCommands) {
        var first = new CommandPermanentHandler(botCommands);
        first.setNext(new LastPermanentHandler());
        return first;
    }
}
