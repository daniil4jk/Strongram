package ru.daniil4jk.strongram.core.command;

import ru.daniil4jk.strongram.core.context.request.RequestContext;

import java.util.function.BiConsumer;

public interface CommandHandler extends BiConsumer<RequestContext, String[]> {
}
