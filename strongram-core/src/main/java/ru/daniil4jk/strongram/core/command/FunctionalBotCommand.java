package ru.daniil4jk.strongram.core.command;

import lombok.*;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import ru.daniil4jk.strongram.core.TelegramUUID;
import ru.daniil4jk.strongram.core.context.BotContext;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class FunctionalBotCommand extends AbstractBotCommand {
    private final BotCommandProcessor action;

    @Builder
    protected FunctionalBotCommand(String commandIdentifier, String description,
                                   BotCommandProcessor action) {
        super(commandIdentifier, description);
        this.action = action;
    }

    @Override
    public BotApiMethod<?> process(TelegramUUID telegramUUID, BotContext context, String[] arguments) {
        return action.process(telegramUUID, context, arguments);
    }

    @FunctionalInterface
    public interface BotCommandProcessor {
        BotApiMethod<?> process(TelegramUUID telegramUUID, BotContext context, String[] arguments);
    }
}
