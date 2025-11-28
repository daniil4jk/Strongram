package ru.daniil4jk.strongram.core.dialog;

import lombok.*;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import ru.daniil4jk.strongram.core.context.BotContext;

@Getter
@ToString
public abstract class MessageDialogPart extends AbstractDialogPart {
    public MessageDialogPart(String onState) {
        super(onState);
    }

    @Override
    public boolean filter(Update update) {
        return super.filter(update) &&
                (update.hasMessage() ||
                 update.hasBusinessMessage() ||
                 update.hasChannelPost());
    }

    @Override
    public final BotApiMethod<?> process(Update update, BotContext botContext, DialogContext dialogContext) {
        Message message = getMessageFromUpdate(update);
        return process(message, botContext, dialogContext);
    }

    private static Message getMessageFromUpdate(Update update) {
        if (update.hasMessage()) return update.getMessage();
        if (update.hasBusinessMessage()) return update.getBusinessMessage();
        if (update.hasChannelPost()) return update.getChannelPost();
        throw new IllegalStateException();
    }

    protected BotApiMethod<?> process(Message message, BotContext botContext, DialogContext dialogContext) {
        return null;
    }

    //todo Functional
}
