package ru.daniil4jk.strongram.command;

import lombok.*;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import ru.daniil4jk.strongram.TelegramUUID;
import ru.daniil4jk.strongram.context.BotContext;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class BotCommandDefaultImpl extends BotCommandImpl{
    private final BotCommandProcessFunction action;

    @Builder
    protected BotCommandDefaultImpl(String commandIdentifier, String description,
                                    BotCommandProcessFunction action) {
        super(commandIdentifier, description);
        this.action = action;
    }

    @Override
    public BotApiMethod<?> process(TelegramUUID telegramUUID, BotContext context, String[] arguments) {
        return action.process(telegramUUID, context, arguments);
    }

    @FunctionalInterface
    public interface BotCommandProcessFunction {
        BotApiMethod<?> process(TelegramUUID telegramUUID, BotContext context, String[] arguments);
    }
}
