package ru.daniil4jk.strongram.handler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.context.BotContext;

@Slf4j
@Setter
@Getter
public abstract class AbstractUpdateHandler implements UpdateHandler {
    private UpdateHandler next;

    protected BotApiMethod<?> processNext(Update update, BotContext context) {
        if (next == null) {
            return null;
        }
        if (log.isDebugEnabled()) {
            log.debug("update processing by handler: {}", next.getClass().getName());
        }
        return next.process(update, context);
    }
}
