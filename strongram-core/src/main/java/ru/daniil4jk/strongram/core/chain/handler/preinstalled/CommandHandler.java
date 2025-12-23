package ru.daniil4jk.strongram.core.chain.handler.preinstalled;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ru.daniil4jk.strongram.core.chain.handler.FilteredHandler;
import ru.daniil4jk.strongram.core.command.CommandStrategy;
import ru.daniil4jk.strongram.core.context.request.RequestContext;
import ru.daniil4jk.strongram.core.filter.Filter;
import ru.daniil4jk.strongram.core.filter.Filters;
import ru.daniil4jk.strongram.core.unboxer.As;
import ru.daniil4jk.strongram.core.util.Lazy;

import java.util.Map;
import java.util.Optional;

@NoArgsConstructor
public abstract class CommandHandler extends FilteredHandler {
    private static final String EMPTY = "";
    private static final String WHITESPACE = " ";
    private static final String DOG = "@";
    private static final String SLASH = "/";

    private final Lazy<Map<String, CommandStrategy>> commands = new Lazy<>(this::getCommands);

    protected abstract Map<String, CommandStrategy> getCommands();

    @Override
    protected final @NotNull Filter getFilter() {
        return Filters.isCommand();
    }

    @Override
    protected final void processFiltered(@NotNull RequestContext ctx) {
        var username = formatUsername(ctx.getBot().getUsername());
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

    private void processCommand(@NotNull RequestContext ctx, @NotNull String text) {
        String[] cmdAndArgs = ctx.getRequest(As.text()).split(WHITESPACE, 2);
        String command = formatCommand(cmdAndArgs[0]);
        String[] args = cmdAndArgs.length > 1 ? cmdAndArgs[1].split(WHITESPACE) : ArrayUtils.EMPTY_STRING_ARRAY;
        parseCommand(command).accept(ctx, args);
    }

    private CommandStrategy parseCommand(String text) {
        return Optional.ofNullable(commands.initOrGet().get(formatCommand(text)))
                .orElseThrow(() -> new CommandNotFoundException(text));
    }

    @Contract(pure = true)
    private static @NotNull String formatUsername(@NotNull String raw) {
        raw = raw.trim().toLowerCase();
        if (raw.startsWith(DOG)) return raw;
        return DOG + raw;
    }

    @Contract(pure = true)
    private static @NotNull String formatCommand(@NotNull String raw) {
        raw = raw.trim();
        if (raw.contains(WHITESPACE)) {
            throw new IllegalArgumentException("Command should not contain whitespace");
        }
        raw = raw.toLowerCase();
        if (raw.startsWith(SLASH)) return raw;
        return SLASH + raw;
    }

    private static class CommandNotFoundException extends RuntimeException {
        public CommandNotFoundException(String command) {
            super("Команда %s не найдена".formatted(command));
        }
    }
}
