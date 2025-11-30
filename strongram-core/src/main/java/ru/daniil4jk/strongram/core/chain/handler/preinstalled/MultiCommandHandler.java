package ru.daniil4jk.strongram.core.chain.handler.preinstalled;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import ru.daniil4jk.strongram.core.chain.caster.As;
import ru.daniil4jk.strongram.core.chain.context.Context;
import ru.daniil4jk.strongram.core.chain.filter.Filter;
import ru.daniil4jk.strongram.core.chain.filter.Filters;
import ru.daniil4jk.strongram.core.chain.filter.WhiteListFilter;
import ru.daniil4jk.strongram.core.chain.handler.BaseHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

@NoArgsConstructor
public class MultiCommandHandler extends BaseHandler {
    private static final String EMPTY = "";
    private static final String WHITESPACE = " ";
    private static final String DOG = "@";
    private static final String SLASH = "/";

    private final Map<String, CommandHandler> commandHandlers = new HashMap<>();
    private final WhiteListFilter<String> commandListFilter = new WhiteListFilter<>(Filters::textStartWith);

    @SafeVarargs
    public MultiCommandHandler(Pair<String, CommandHandler> @NotNull ... commands) {
        for (Pair<String, CommandHandler> pair : commands) {
            addCommand(pair.getKey(), pair.getValue());
        }
    }

    public void addCommand(String command, CommandHandler handler) {
        command = formatCommand(command);
        commandHandlers.put(command, handler);
        commandListFilter.add(command);
    }

    public void removeCommand(String command) {
        command = formatCommand(command);
        commandHandlers.remove(command);
        commandListFilter.remove(command);
    }

    @Override
    protected final @NotNull Filter getFilter() {
        return Filters.textStartWith(SLASH).and(commandListFilter);
    }

    @Override
    protected final void process(@NotNull Context ctx) {
        var username = formatUsername(ctx.getCredentials().getUsername());
        var msg = ctx.getRequest(As.message());
        var text = msg.getText();


        try {
            processCommand(ctx, text);
        } catch (CommandNotFoundException e) {
            try {
                processGroupCommand(ctx, text, username);
            } catch (CommandNotFoundException ee) {
                processNext(ctx);
            }
        }
    }

    private void processGroupCommand(Context ctx, @NotNull String text, String username) {
        String groupText = text.replace(username, EMPTY);
        processCommand(ctx, groupText);
    }

    private void processCommand(Context ctx, @NotNull String text) {
        parseCommand(text.split(WHITESPACE)[0]).accept(ctx, parseArgs(text));
    }

    private CommandHandler parseCommand(String text) {
        return Optional.ofNullable(commandHandlers.get(formatCommand(text)))
                .orElseThrow(() -> new CommandNotFoundException(text));
    }

    private static String @NotNull [] parseArgs(@NotNull String text) {
        String[] args = text.split(WHITESPACE);
        return Arrays.copyOfRange(args, 1, args.length);
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

    public interface CommandHandler extends BiConsumer<Context, String[]> {}

    public static class CommandNotFoundException extends RuntimeException {
        public CommandNotFoundException(String command) {
            super("Команда %s не найдена".formatted(command));
        }
    }
}
