package ru.daniil4jk.strongram.core.command;

import ru.daniil4jk.strongram.core.chain.context.RequestContext;

import java.util.function.BiConsumer;

public interface CommandHandler extends BiConsumer<RequestContext, String[]> {
}
