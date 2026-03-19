package ru.daniil4jk.strongram.core.upstream.preinstalled;

import lombok.RequiredArgsConstructor;
import ru.daniil4jk.strongram.core.context.request.RequestContext;
import ru.daniil4jk.strongram.core.unboxer.As;
import ru.daniil4jk.strongram.core.upstream.BaseUpstreamHandler;

@RequiredArgsConstructor
public class CannotProcessUpstreamHandler extends BaseUpstreamHandler {
    private final String text;

    public CannotProcessUpstreamHandler() {
        this.text = "Я не понимаю ваш запрос\uD83D\uDE14 / I cat`t explain this request\uD83D\uDE14";
    }

    @Override
    protected final void process(RequestContext ctx) {
        try {
            if (ctx.getUUID().chat().isUserChat()) {
                ctx.getResponder().send(text);
                return;
            }
        } catch (Exception e) { /* предназначено не нам, игнорим */ }
        try {
            if (ctx.getRequest(As.messageText()) != null) {
                ctx.getResponder().send(text);
            }
        } catch (Exception e) { /* предназначено не нам, игнорим */ }
    }
}
