package ru.daniil4jk.strongram.core.dialog;

import ru.daniil4jk.strongram.core.chain.context.RequestContext;

import java.util.function.Consumer;

public interface Dialog extends Consumer<RequestContext> {
    void sendNotification(RequestContext ctx);
    boolean isStopped();
    boolean canAccept(RequestContext ctx);
    Object getLock();
}
