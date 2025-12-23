package ru.daniil4jk.strongram.core.command;

import ru.daniil4jk.strongram.core.context.request.RequestContext;

import java.util.function.Consumer;

public interface TextCommandStrategy extends Consumer<RequestContext> {
}
