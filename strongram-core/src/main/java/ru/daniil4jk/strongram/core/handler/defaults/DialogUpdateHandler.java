package ru.daniil4jk.strongram.core.handler.defaults;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.core.TelegramUUID;
import ru.daniil4jk.strongram.core.context.BotContext;
import ru.daniil4jk.strongram.core.dialog.CannotProcessCaseException;
import ru.daniil4jk.strongram.core.dialog.DialogRegistry;
import ru.daniil4jk.strongram.core.handler.AbstractUpdateHandler;
import ru.daniil4jk.strongram.core.parser.ParserService;
import ru.daniil4jk.strongram.core.parser.TelegramObjectParseException;
import ru.daniil4jk.strongram.core.parser.uuid.TelegramUUIDParserService;

public class DialogUpdateHandler extends AbstractUpdateHandler {
    private final ParserService<TelegramUUID> UUIDParser =
            TelegramUUIDParserService.getInstance();

    @Override
    public BotApiMethod<?> execute(Update update, BotContext context) {
        DialogRegistry registry = context.getByClass(DialogRegistry.class);

        TelegramUUID uuid;
        try {
            uuid = UUIDParser.parse(update);
        } catch (TelegramObjectParseException e) {
            return processNext(update, context);
        }

        for (var dialog : registry.getAllByUUID(uuid)) {
            synchronized (dialog) {
                if (!registry.contains(uuid, dialog)) {
                    //if thread in synchronized block reach dialog, that already removed while it waiting
                    continue;
                }

                try {
                    if (dialog.canProcess(update)) {
                        BotApiMethod<?> result = dialog.process(update, context);
                        if (dialog.isCompleted()) {
                            registry.remove(uuid, dialog);
                        }
                        return result;
                    }
                } catch (CannotProcessCaseException e) {
                    continue;
                }
            }
        }

        return processNext(update, context);
    }
}
