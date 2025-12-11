package ru.daniil4jk.strongram.core.chain.handler.preinstalled;

import lombok.RequiredArgsConstructor;
import ru.daniil4jk.strongram.core.chain.transformer.As;
import ru.daniil4jk.strongram.core.chain.context.RequestContext;
import ru.daniil4jk.strongram.core.chain.handler.BaseHandler;

@RequiredArgsConstructor
public class CannotProcessHandler extends BaseHandler {
    private final String text;

    public CannotProcessHandler() {
        this.text = "Я не понимаю ваш запрос\uD83D\uDE14 / I cat`t explain this request\uD83D\uDE14";
    }

    @Override
    protected final void process(RequestContext ctx) {
        try {
            if (ctx.getUserId().chat().isUserChat()) {
                ctx.respond(text);
                return;
            }
        } catch (Exception e) { /* предназначено не нам, игнорим */ }
        try {
            if (ctx.getRequest(As.messageText()) != null) {
                ctx.respond(text);
            }
        } catch (Exception e) { /* предназначено не нам, игнорим */ }
    }
}
