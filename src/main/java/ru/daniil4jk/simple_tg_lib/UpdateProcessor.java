package ru.daniil4jk.simple_tg_lib;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.simple_tg_lib.handler.one_time.OneTimeHandlerService;
import ru.daniil4jk.simple_tg_lib.handler.one_time.OneTimeHandlerServiceImpl;
import ru.daniil4jk.simple_tg_lib.handler.permanent.PermanentHandler;
import ru.daniil4jk.simple_tg_lib.parser.ParserService;

public class UpdateProcessor {
    private OneTimeHandlerService oneTimeHandlerService;
    private PermanentHandler permanentHandlerChain;
    private ParserService<TelegramUUID> UUIDParserService;

    public UpdateProcessor() {
        oneTimeHandlerService = new OneTimeHandlerServiceImpl();
    }

    public void process(Update update) {
        TelegramUUID id = UUIDParserService.parse(update);
        boolean served = false;
        if (oneTimeHandlerService.containsByUUID(id)) {
            served = processOneTimeHandler(id, update);
        }
        if (!served) {
            permanentHandlerChain.process(update);
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
