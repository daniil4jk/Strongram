package ru.daniil4jk.strongram.handler.permanent;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.context.BotContext;

@Setter
@Getter
public abstract class AbstractPermanentHandler implements PermanentHandler {
    private PermanentHandler next;

    protected BotApiMethod<?> processNext(Update update, BotContext context) {
        if (next == null) {
            return null;
        }
        return next.process(update, context);
    }
}
