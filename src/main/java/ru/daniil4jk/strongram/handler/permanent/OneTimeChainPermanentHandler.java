package ru.daniil4jk.strongram.handler.permanent;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.TelegramUUID;
import ru.daniil4jk.strongram.context.BotContext;
import ru.daniil4jk.strongram.handler.one_time.OneTimeHandlerRegistry;
import ru.daniil4jk.strongram.parser.ParserService;

public class OneTimeChainPermanentHandler extends AbstractPermanentHandler {
    private final ParserService<TelegramUUID> telegramUUIDParser;

    @Override
    public BotApiMethod<?> process(Update update, @NotNull BotContext context) {
        TelegramUUID uuid = telegramUUIDParser.parse(update);
        var registry = context.getByClass(OneTimeHandlerRegistry.class);

        if (registry.containsByUUID(uuid)) {
            for (var handler : registry.getAllByUUID(uuid)) {
                try {
                    if (handler.filter(update, context)) {
                        var response = handler.process(update, context);
                        registry.remove(uuid, handler);
                        return response;
                    }
                } catch (Exception e) {
                    handler.onException(e);
                    if (handler.isRemoveOnException()) {
                        registry.remove(uuid, handler);
                    }
                }
            }
        }

        return processNext(update, context);
    }
}
