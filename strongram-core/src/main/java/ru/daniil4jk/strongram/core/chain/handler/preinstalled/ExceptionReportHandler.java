package ru.daniil4jk.strongram.core.chain.handler.preinstalled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.daniil4jk.strongram.core.chain.handler.BaseHandler;
import ru.daniil4jk.strongram.core.context.request.RequestContext;
import ru.daniil4jk.strongram.core.report.exception.ExceptionFormatter;
import ru.daniil4jk.strongram.core.report.exception.ExceptionFormatters;

@Slf4j
@RequiredArgsConstructor
public class ExceptionReportHandler extends BaseHandler {
    private final ExceptionFormatter formatter;

    public ExceptionReportHandler() {
        formatter = ExceptionFormatters.info();
    }

    @Override
    protected final void process(RequestContext ctx) {
        try {
            processNext(ctx);
        } catch (Exception e) {
            log.error("Произошла ошибка внутри цепочки", e);
            try {
                ctx.getSender().send(formatter.apply(e));
            } catch (Exception e2) { /* не можем отправить сообщение об ошибке, всё очень плохо */ }
        }
    }
}