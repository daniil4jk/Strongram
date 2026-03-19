package ru.daniil4jk.strongram.core.downstream;

import ru.daniil4jk.strongram.core.context.request.RequestContext;
import ru.daniil4jk.strongram.core.response.dto.Response;
import ru.daniil4jk.strongram.core.response.sender.ResponseSink;
import ru.daniil4jk.strongram.core.util.Lazy;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class CallbackWrapper {
    private final Lazy<List<DownstreamHandler>> downstreamChain;

    public CallbackWrapper(Supplier<List<DownstreamHandler>> downstreamCreateMethod) {
        downstreamChain = new Lazy<>(downstreamCreateMethod);
    }

    public ResponseSink wrap(ResponseSink toBot) {
        return wrap(null, toBot);
    }

    public ResponseSink wrap(RequestContext ctx, ResponseSink toBot) {
        Optional<RequestContext> ctxOptional = Optional.ofNullable(ctx);
        return responses -> {
            for (Response<?> r : responses) {
                for (DownstreamHandler h : downstreamChain.initOrGet()) {
                    h.accept(ctxOptional, r.getEntry());
                }
            }
        };
    }
}
