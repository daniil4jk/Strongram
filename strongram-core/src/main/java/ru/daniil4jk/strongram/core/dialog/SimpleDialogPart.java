package ru.daniil4jk.strongram.core.dialog;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import ru.daniil4jk.strongram.core.context.BotContext;
import ru.daniil4jk.strongram.core.dto.TelegramUUID;
import ru.daniil4jk.strongram.core.parser.uuid.TelegramUUIDParserService;

@Getter
@ToString
public abstract class SimpleDialogPart extends MessageDialogPart {
    public SimpleDialogPart(String onState) {
        super(onState);
    }

    @Override
    protected final BotApiMethod<?> process(Message message, BotContext botContext, DialogContext dialogContext) {
        TelegramUUID uuid = TelegramUUIDParserService.getInstance().parse(message);
        return new SendMessage(
                uuid.getChatId().toString(),
                processToString(message, botContext, dialogContext)
        );
    }

    protected String processToString(Message message, BotContext botContext, DialogContext dialogContext) {
        return null;
    }

    //todo Functional
}
