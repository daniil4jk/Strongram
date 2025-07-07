package ru.daniil4jk.strongram.handler;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.TelegramUUID;
import ru.daniil4jk.strongram.command.BotCommandImpl;
import ru.daniil4jk.strongram.command.CommandRegistry;
import ru.daniil4jk.strongram.context.BotContext;
import ru.daniil4jk.strongram.parser.ParserService;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class CommandUpdateHandler extends AbstractUpdateHandler {
    private final ParserService<TelegramUUID> telegramUUIDParser;
    private final boolean allowCommandsWithUsername;
    private final Supplier<String> botUsernameSupplier;

    public CommandUpdateHandler(boolean allowCommandsWithUsername, Supplier<String> botUsernameSupplier) {
        this.allowCommandsWithUsername = allowCommandsWithUsername;
        this. botUsernameSupplier =  botUsernameSupplier;
    }

    @Override
    public BotApiMethod<?> process(@NotNull Update update, BotContext context) {
        if (update.hasMessage() && update.getMessage().getText()
                .startsWith(BotCommandImpl.COMMAND_INIT_CHARACTER)) {
            try {
                return processCommand(telegramUUIDParser.parse(update),
                        update.getMessage().getText(), context);
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
        String[] commandSplit = commandMessage.split(BotCommandImpl.COMMAND_PARAMETER_SEPARATOR_REGEXP);

        String commandIdentifier = removeUsernameFromCommandIfNeeded(commandSplit[0]);

        if (registry.contains(commandIdentifier)) {
            String[] parameters = Arrays.copyOfRange(commandSplit, 1, commandSplit.length);
            return registry.get(commandIdentifier).process(uuid, context, parameters);
        } else {
            throw new CommandNotFoundException();
        }
    }

    private static class CommandNotFoundException extends RuntimeException {}

    private String removeUsernameFromCommandIfNeeded(String command) {
        if (allowCommandsWithUsername) {
            String botUsername = Objects.requireNonNull(botUsernameSupplier.get(),
                    "TelegramBot username must not be null");
            return command.replaceAll("(?i)@" + Pattern.quote(botUsername), "").trim();
        }
        return command;
    }
}
