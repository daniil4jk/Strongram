package ru.daniil4jk.strongram.handler.one_time;

import lombok.*;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.context.BotContext;

import java.util.function.*;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public final class OneTimeHandlerImpl implements OneTimeHandler {
    @Getter
    private final BotApiMethodMessage firstNotification;
    @Getter
    private final BotApiMethodMessage notification;
    private final BiPredicate<Update, BotContext> filter;
    private final BiFunction<Update, BotContext, BotApiMethod<?>> process;
    private final Consumer<Exception> onException;
    @Getter
    private final boolean removeOnException;

    @Override
    public boolean filter(Update update, BotContext context) {
        return filter.test(update, context);
    }

    @Override
    public BotApiMethod<?> process(Update update, BotContext context) {
        return process.apply(update, context);
    }

    @Override
    public void onException(Exception e) {
        onException.accept(e);
    }
}
