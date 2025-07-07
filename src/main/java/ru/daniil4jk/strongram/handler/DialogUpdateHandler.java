package ru.daniil4jk.strongram.handler;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.TelegramUUID;
import ru.daniil4jk.strongram.context.BotContext;
import ru.daniil4jk.strongram.dialog.DialogRegistry;
import ru.daniil4jk.strongram.parser.ParserService;

public class DialogUpdateHandler extends AbstractUpdateHandler {
    private final ParserService<TelegramUUID> telegramUUIDParser;

    @Override
    public BotApiMethod<?> process(Update update, BotContext context) {
        DialogRegistry registry = context.getByClass(DialogRegistry.class);
        TelegramUUID uuid = telegramUUIDParser.parse(update);

        for (var dialog : registry.getAllByUUID(uuid)) {
            synchronized (dialog) {
                if (!registry.contains(uuid, dialog)) {
                    continue;
                }

                if (dialog.canProcess(update)) {
                    BotApiMethod<?> result = dialog.process(update, context);
                    if (dialog.isCompleted()) {
                        registry.remove(uuid, dialog);
                    }
                    return result;
                }
            }
        }

        return processNext(update, context);
    }
}
