package ru.daniil4jk.strongram.core.upstream;

import ru.daniil4jk.strongram.core.chain.NextConsumer;
import ru.daniil4jk.strongram.core.context.request.RequestContext;

import java.util.function.Consumer;

public interface UpstreamHandler extends Consumer<RequestContext>, NextConsumer<UpstreamHandler> {
}
