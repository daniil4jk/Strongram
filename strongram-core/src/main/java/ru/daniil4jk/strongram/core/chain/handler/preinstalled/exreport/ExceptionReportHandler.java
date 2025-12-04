package ru.daniil4jk.strongram.core.chain.handler.preinstalled.exreport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.daniil4jk.strongram.core.chain.context.RequestContext;
import ru.daniil4jk.strongram.core.chain.handler.BaseHandler;

@Slf4j
@RequiredArgsConstructor
public final class ExceptionReportHandler extends BaseHandler {
    private final ExceptionFormatter formatter;

    public ExceptionReportHandler() {
        formatter = ExceptionFormatters.info();
    }

    @Override
    protected void process(RequestContext ctx) {
        try {
            processNext(ctx);
        } catch (Exception e) {
            log.error("Произошла ошибка внутри цепочки", e);
            try {
                ctx.respond(formatter.apply(e));
            } catch (Exception e2) { /* не можем отправить сообщение об ошибке, всё очень плохо */ }
        }
    }
}