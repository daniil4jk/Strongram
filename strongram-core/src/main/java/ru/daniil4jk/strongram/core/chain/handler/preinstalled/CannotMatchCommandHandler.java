package ru.daniil4jk.strongram.core.chain.handler.preinstalled;

import lombok.RequiredArgsConstructor;
import ru.daniil4jk.strongram.core.chain.caster.As;
import ru.daniil4jk.strongram.core.chain.context.Context;
import ru.daniil4jk.strongram.core.chain.handler.BaseHandler;

@RequiredArgsConstructor
public final class CannotMatchCommandHandler extends BaseHandler {
    private final String text;

    public CannotMatchCommandHandler() {
        this.text = "Я не понимаю ваш запрос\uD83D\uDE14 / I cat`t explain this request\uD83D\uDE14";
    }

    @Override
    protected void process(Context ctx) {
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
