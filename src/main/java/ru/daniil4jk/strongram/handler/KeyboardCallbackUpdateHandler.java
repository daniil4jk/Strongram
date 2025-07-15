package ru.daniil4jk.strongram.handler;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.context.BotContext;
import ru.daniil4jk.strongram.keyboard.ButtonCallbackAction;
import ru.daniil4jk.strongram.keyboard.ButtonWithCallbackRegistry;
import ru.daniil4jk.strongram.parser.ParserService;
import ru.daniil4jk.strongram.parser.TelegramObjectParseException;
import ru.daniil4jk.strongram.parser.payload.PayloadParserService;

public class KeyboardCallbackUpdateHandler extends AbstractUpdateHandler {
    private final ParserService<String> textParser = PayloadParserService.getInstance();


    @Override
    public BotApiMethod<?> process(@NotNull Update update, BotContext context) {
        if (!(update.hasMessage() || update.hasCallbackQuery())) {
            return processNext(update, context);
        }

        String callbackId;
        try {
            callbackId = textParser.parse(update);
        } catch (TelegramObjectParseException e) {
            return processNext(update, context);
        }

        var registry = context.getByClass(ButtonWithCallbackRegistry.class);

        if (!registry.contains(callbackId)) {
            return processNext(update, context);
        }
        return processAction(registry, callbackId);
    }

    private BotApiMethod<?> processAction(ButtonWithCallbackRegistry registry,
                                          String callbackId) {
        ButtonCallbackAction action = registry.get(callbackId);

        try {
            var result = action.run();
            removeIfDisposable(registry, callbackId, action);
            return result;
        } catch (Exception e) {
            var result = action.onException(e);
            removeIfDisposable(registry, callbackId, action);
            return result;
        }
    }

    private void removeIfDisposable(ButtonWithCallbackRegistry registry,
                                    String callbackId, ButtonCallbackAction action) {
        if (action.isDisposable()) {
            registry.remove(callbackId, action);
        }
    }
}
