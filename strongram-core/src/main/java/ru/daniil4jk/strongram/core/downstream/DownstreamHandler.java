package ru.daniil4jk.strongram.core.downstream;

import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import ru.daniil4jk.strongram.core.context.request.RequestContext;

import java.util.Optional;
import java.util.function.BiConsumer;

public interface DownstreamHandler extends BiConsumer<Optional<RequestContext>, PartialBotApiMethod<?>> {
}
