package ru.daniil4jk.strongram.core.chain.handler.preinstalled;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ru.daniil4jk.strongram.core.chain.handler.FilteredHandler;
import ru.daniil4jk.strongram.core.command.EachCommandHandler;
import ru.daniil4jk.strongram.core.context.request.RequestContext;
import ru.daniil4jk.strongram.core.filter.Filter;
import ru.daniil4jk.strongram.core.filter.Filters;
import ru.daniil4jk.strongram.core.unboxer.As;
import ru.daniil4jk.strongram.core.util.Lazy;

import java.util.Map;
import java.util.stream.Collectors;

public abstract class TextCommandHandler extends FilteredHandler {
    private static final String EMPTY = "";
    private static final String WHITESPACE = " ";

    private final Lazy<Map<String, EachCommandHandler>> commands = new Lazy<>(this::getFormattedCommands);

    private Map<String, EachCommandHandler> getFormattedCommands() {
        return getCommands().entrySet().stream()
                .map(entry -> Map.entry(formatCommand(entry.getKey()), entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    protected abstract Map<String, EachCommandHandler> getCommands();

    @Override
    protected @NotNull Filter getFilter() {
        return Filters.hasMessageText().and(ctx ->
                isCommand(ctx.getRequest(As.messageText()))
        );
    }

    private boolean isCommand(@NotNull String text) {
        String command = formatCommand(text.split(WHITESPACE, 2)[0]);
        return commands.initOrGet().containsKey(command);
    }

    @Override
    protected void processFiltered(@NotNull RequestContext ctx) {
        String[] cmdAndArgs = ctx.getRequest(As.text()).split(WHITESPACE, 2);
        String command = formatCommand(cmdAndArgs[0]);
        String[] args = cmdAndArgs.length > 1 ? cmdAndArgs[1].split(WHITESPACE) : ArrayUtils.EMPTY_STRING_ARRAY;

        commands.initOrGet()
                .get(command)
                .accept(ctx, args);
    }

    @Contract(pure = true)
    private static @NotNull String formatCommand(@NotNull String rawCommand) {
        rawCommand = rawCommand.trim();
        if (rawCommand.contains(WHITESPACE)) {
            throw new IllegalArgumentException("Command should not contain whitespace");
        }
        return rawCommand.toLowerCase();
    }
}
