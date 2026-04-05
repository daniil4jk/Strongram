package ru.daniil4jk.strongram.core.downstream;

import ru.daniil4jk.strongram.core.context.request.RequestContext;
import ru.daniil4jk.strongram.core.response.sender.ResponseSink;
import ru.daniil4jk.strongram.core.util.Lazy;

import java.util.List;
import java.util.Optional;

public class CallbackWrapper {
    private final Lazy<List<DownstreamHandler>> downstreamChain;

    public CallbackWrapper(Lazy<List<DownstreamHandler>> downstreamChain) {
        this.downstreamChain = downstreamChain;
    }

    public ResponseSink wrap(ResponseSink toBot) {
        return wrap(null, toBot);
    }

    public ResponseSink wrap(RequestContext ctx, ResponseSink toBot) {
        Optional<RequestContext> ctxOptional = Optional.ofNullable(ctx);
        return r -> {
            for (DownstreamHandler h : downstreamChain.initOrGet()) {
                h.accept(ctxOptional, r.getEntry());
            }
            toBot.accept(r);
        };
    }
}
