package ru.daniil4jk.strongram.handler.permanent;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.context.BotContext;
import ru.daniil4jk.strongram.keyboard.ButtonWithCallback;
import ru.daniil4jk.strongram.keyboard.ButtonWithCallbackRegistry;
import ru.daniil4jk.strongram.parser.ParserService;

public class KeyboardCallbackPermanentHandler extends AbstractPermanentHandler {
    private final ParserService<String> textParser;


    @Override
    public BotApiMethod<?> process(@NotNull Update update, BotContext context) {
        if (!(update.hasMessage() || update.hasCallbackQuery())) {
            return processNext(update, context);
        }

        String callbackData = textParser.parse(update);
        var registry = context.getByClass(ButtonWithCallbackRegistry.class);
        if (!registry.contains(callbackData)) {
            return processNext(update, context);
        }

        ButtonWithCallback button = registry.get(callbackData);
        try {
            button.callback();
            registry.remove(button);
        } catch (Exception e) {
            button.onException(e);
            if (button.isRemoveOnException()) {
                registry.remove(button);
            }
        }

        return null;
    }
}
