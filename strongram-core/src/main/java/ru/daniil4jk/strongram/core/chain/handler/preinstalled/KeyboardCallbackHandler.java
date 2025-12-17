package ru.daniil4jk.strongram.core.chain.handler.preinstalled;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.daniil4jk.strongram.core.chain.handler.FilteredHandler;
import ru.daniil4jk.strongram.core.context.request.RequestContext;
import ru.daniil4jk.strongram.core.filter.Filter;
import ru.daniil4jk.strongram.core.filter.Filters;
import ru.daniil4jk.strongram.core.keyboard.InteractiveKeyboardHolder;
import ru.daniil4jk.strongram.core.keyboard.button.InteractiveButton;
import ru.daniil4jk.strongram.core.unboxer.As;
import ru.daniil4jk.strongram.core.util.Lazy;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class KeyboardCallbackHandler extends FilteredHandler {
    private final Lazy<Map<String, InteractiveButton>> buttons = new Lazy<>(this::parseKeyboard);

    protected abstract InteractiveKeyboardHolder getKeyboard();

    @Override
    protected @NotNull Filter getFilter() {
        return Filters.hasText().and(
                ctx -> buttons.initOrGet().containsKey(
                        ctx.getRequest(As.text())
                )
        );
    }

    @Override
    protected void processFiltered(@NotNull RequestContext ctx) {
        buttons.initOrGet().get(ctx.getRequest(As.text())).accept(ctx);
    }

    protected Map<String, InteractiveButton> parseKeyboard() {
        InteractiveKeyboardHolder holder = getKeyboard();
        ReplyKeyboard keyboard = holder.getKeyboard();

        return switch (holder.getType()) {
            case Reply -> collectButtons(((ReplyKeyboardMarkup) keyboard).getKeyboard().stream());
            case Inline -> collectButtons(((InlineKeyboardMarkup) keyboard).getKeyboard().stream());
        };
    }

    private static Map<String, InteractiveButton> collectButtons(Stream<? extends List<?>> stream) {
        Map<String, InteractiveButton> map = stream
                .flatMap(Collection::stream)
                .filter(InteractiveButton::isInstance)
                .map(InteractiveButton::cast)
                .collect(mapCollector);

        if (map.isEmpty()) {
            throw new IllegalArgumentException("keyboard has`nt interactive buttons");
        }
        return map;
    }

    private static final Collector<InteractiveButton, ?, Map<String, InteractiveButton>> mapCollector =
            Collectors.toMap(InteractiveButton::getCallbackData, button -> button);
}
