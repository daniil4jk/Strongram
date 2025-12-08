package ru.daniil4jk.strongram.core.chain.handler.preinstalled;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.daniil4jk.strongram.core.chain.caster.As;
import ru.daniil4jk.strongram.core.chain.context.RequestContext;
import ru.daniil4jk.strongram.core.chain.filter.Filter;
import ru.daniil4jk.strongram.core.chain.filter.Filters;
import ru.daniil4jk.strongram.core.chain.handler.FiteredHandler;
import ru.daniil4jk.strongram.core.command.CommandHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@NoArgsConstructor
public final class MultiCommandHandler extends FiteredHandler {
    private static final String EMPTY = "";
    private static final String WHITESPACE = " ";
    private static final String DOG = "@";
    private static final String SLASH = "/";

    private final Map<String, CommandHandler> commandHandlers = new HashMap<>();

    private final Filter messageIsCommandFilter = Filters.textStartWith(SLASH).and(
            ctx -> commandHandlers.containsKey(
                    ctx.getRequest(As.text())
            )
    );

    public MultiCommandHandler(Map<String, CommandHandler> commands) {
        commandHandlers.putAll(commands);
    }

    public void addCommand(String command, CommandHandler handler) {
        command = formatCommand(command);
        commandHandlers.put(command, handler);
    }

    public void removeCommand(String command) {
        command = formatCommand(command);
        commandHandlers.remove(command);
    }

    @Override
    protected Filter getFilter() {
        return messageIsCommandFilter;
    }

    @Override
    protected void processFiltered(@NotNull RequestContext ctx) {
        var username = formatUsername(ctx.getCredentials().getUsername());
        var msg = ctx.getRequest(As.message());
        var text = msg.getText();


        try {
            processCommand(ctx, text);
            return;
        } catch (CommandNotFoundException e) { /* продолжаем выполнение */ }

        try {
            processGroupCommand(ctx, text, username);
            return;
        } catch (CommandNotFoundException ee) { /* продолжаем выполнение */ }

        processNext(ctx);
    }

    private void processGroupCommand(RequestContext ctx, @NotNull String text, String username) {
        String groupText = text.replace(username, EMPTY);
        processCommand(ctx, groupText);
    }

    private void processCommand(RequestContext ctx, @NotNull String text) {
        String[] split = text.split(WHITESPACE);
        String commandName = split[0];
        String[] args = Arrays.copyOfRange(split, 1, split.length);
        parseCommand(commandName).accept(ctx, args);
    }

    private CommandHandler parseCommand(String text) {
        return Optional.ofNullable(commandHandlers.get(formatCommand(text)))
                .orElseThrow(() -> new CommandNotFoundException(text));
    }

    private static @NotNull String formatUsername(String raw) {
        raw = raw.trim().toLowerCase();
        if (raw.startsWith(DOG)) return raw;
        return DOG + raw;
    }

    private static @NotNull String formatCommand(String raw) {
        raw = raw.trim().toLowerCase();
        if (raw.startsWith(SLASH)) return raw;
        return SLASH + raw;
    }

    private static class CommandNotFoundException extends RuntimeException {
        public CommandNotFoundException(String command) {
            super("Команда %s не найдена".formatted(command));
        }
    }
}
