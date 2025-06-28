package ru.daniil4jk.simple_tg_lib;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.daniil4jk.simple_tg_lib.handler.one_time.OneTimeHandlerService;
import ru.daniil4jk.simple_tg_lib.handler.one_time.OneTimeHandlerServiceImpl;
import ru.daniil4jk.simple_tg_lib.handler.permanent.PermanentHandler;
import ru.daniil4jk.simple_tg_lib.parser.ParserService;

import java.util.Collection;

public class UpdateProcessor {
    private AbsSender botWhoUsingThis;
    private final OneTimeHandlerService oneTimeHandlerService;
    private final PermanentHandler permanentHandlerChain;
    private ParserService<TelegramUUID> UUIDParserService;

    public UpdateProcessor(Collection<BotCommand> botCommands) {
        oneTimeHandlerService = new OneTimeHandlerServiceImpl();
        permanentHandlerChain = PermanentHandler.defaultPermanentHandlerChain(botCommands);
    }

    public UpdateProcessor(OneTimeHandlerService oneTimeHandlerService, PermanentHandler permanentHandlerChain) {
        this.oneTimeHandlerService = oneTimeHandlerService;
        this.permanentHandlerChain = permanentHandlerChain;
    }

    public void process(AbsSender absSender, Update update) {
        if (botWhoUsingThis == null) {
            botWhoUsingThis = absSender;
        }
        if (absSender != botWhoUsingThis) {
            throw new IllegalCallerException("One UpdateProcessor cannot using in many bot`s. " +
                    "Please create new UpdateProcessor for each bot");
        }

        TelegramUUID id = UUIDParserService.parse(update);
        boolean served = false;
        if (oneTimeHandlerService.containsByUUID(id)) {
            served = processOneTimeHandler(id, update);
        }
        if (!served) {
            permanentHandlerChain.process(absSender, update);
        }
    }

    private boolean processOneTimeHandler(TelegramUUID id, Update update) {
        for (var handler : oneTimeHandlerService.getAll(id)) {
            if (handler.canProcess(update)) {
                try {
                    handler.process(update);
                    oneTimeHandlerService.remove(id, handler);
                } catch (Exception e) {
                    handler.onException(e);
                    if (handler.removeOnException()) {
                        oneTimeHandlerService.remove(id, handler);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
