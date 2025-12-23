package ru.daniil4jk.strongram.core.chain.handler.preinstalled;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ru.daniil4jk.strongram.core.chain.handler.FilteredHandler;
import ru.daniil4jk.strongram.core.command.TextCommandStrategy;
import ru.daniil4jk.strongram.core.context.request.RequestContext;
import ru.daniil4jk.strongram.core.filter.Filter;
import ru.daniil4jk.strongram.core.filter.Filters;
import ru.daniil4jk.strongram.core.unboxer.As;
import ru.daniil4jk.strongram.core.util.Lazy;

import java.util.Map;
import java.util.stream.Collectors;

public abstract class TextCommandHandler extends FilteredHandler {
    private final Lazy<Map<String, TextCommandStrategy>> commands = new Lazy<>(this::getFormattedCommands);

    private Map<String, TextCommandStrategy> getFormattedCommands() {
        return getCommands().entrySet().stream()
                .map(entry -> Map.entry(formatCommand(entry.getKey()), entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    protected abstract Map<String, TextCommandStrategy> getCommands();

    @Override
    protected @NotNull Filter getFilter() {
        return Filters.hasMessageText().and(ctx ->
                commands.initOrGet().containsKey(formatCommand(ctx.getRequest(As.text())))
        );
    }

    @Override
    protected void processFiltered(@NotNull RequestContext ctx) {
        commands.initOrGet()
                .get(formatCommand(ctx.getRequest(As.text())))
                .accept(ctx);
    }

    @Contract(pure = true)
    private static @NotNull String formatCommand(@NotNull String command) {
        return command.toLowerCase();
    }
}
