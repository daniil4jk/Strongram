package ru.daniil4jk.strongram.core.handler.defaults;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.core.TelegramUUID;
import ru.daniil4jk.strongram.core.command.AbstractBotCommand;
import ru.daniil4jk.strongram.core.command.CommandRegistry;
import ru.daniil4jk.strongram.core.context.BotContext;
import ru.daniil4jk.strongram.core.handler.AbstractUpdateHandler;
import ru.daniil4jk.strongram.core.parser.ParserService;
import ru.daniil4jk.strongram.core.parser.TelegramObjectParseException;
import ru.daniil4jk.strongram.core.parser.uuid.TelegramUUIDParserService;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.regex.Pattern;

@Slf4j
public class CommandUpdateHandler extends AbstractUpdateHandler {
    private final ParserService<TelegramUUID> UUIDParser =
            TelegramUUIDParserService.getInstance();
    private final boolean allowCommandsWithUsername;
    private final Supplier<String> botUsernameSupplier;

    public CommandUpdateHandler(boolean allowCommandsWithUsername, Supplier<String> botUsernameSupplier) {
        this.allowCommandsWithUsername = allowCommandsWithUsername;
        this.botUsernameSupplier = botUsernameSupplier;
    }

    @Override
    public BotApiMethod<?> execute(@NotNull Update update, BotContext context) {
        if (update.hasMessage() && update.getMessage().getText()
                .startsWith(AbstractBotCommand.COMMAND_INIT_CHARACTER)) {

            TelegramUUID uuid;
            try {
                uuid = UUIDParser.parse(update);
            } catch (TelegramObjectParseException e) {
                return processNext(update, context);
            }

            try {
                return processCommand(uuid, update.getMessage().getText(), context);
            } catch (CommandNotFoundException e) {
                return processNext(update, context);
            }

        } else {
            return processNext(update, context);
        }
    }

    private BotApiMethod<?> processCommand(TelegramUUID uuid, @NotNull String text, @NotNull BotContext context) {
        var registry = context.getByClass(CommandRegistry.class);

        String commandMessage = text.substring(1);
        String[] commandSplit = commandMessage.split(AbstractBotCommand.COMMAND_PARAMETER_SEPARATOR_REGEXP);

        String commandIdentifier = removeUsernameFromCommandIfNeeded(commandSplit[0]);

        if (registry.contains(commandIdentifier)) {
            if (log.isDebugEnabled()) {
                log.debug("Update text {} fits commandIdentifier {}", text, commandIdentifier);
            }
            String[] parameters = Arrays.copyOfRange(commandSplit, 1, commandSplit.length);
            return registry.get(commandIdentifier).process(uuid, context, parameters);
        } else {
            throw new CommandNotFoundException();
        }
    }

    private String removeUsernameFromCommandIfNeeded(String command) {
        if (allowCommandsWithUsername) {
            String botUsername = Objects.requireNonNull(botUsernameSupplier.get(),
                    "StrongramBot username must not be null");
            return command.replaceAll("(?i)@" + Pattern.quote(botUsername), "").trim();
        }
        return command;
    }

    private static class CommandNotFoundException extends RuntimeException {
    }
}
