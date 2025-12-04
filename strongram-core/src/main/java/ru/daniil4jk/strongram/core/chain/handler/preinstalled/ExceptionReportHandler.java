package ru.daniil4jk.strongram.core.chain.handler.preinstalled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ru.daniil4jk.strongram.core.chain.context.RequestContext;
import ru.daniil4jk.strongram.core.chain.handler.BaseHandler;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
public final class ExceptionReportHandler extends BaseHandler {
    private final ExceptionFormatter formatter;

    public ExceptionReportHandler() {
        formatter = Formatters.info();
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

    public interface ExceptionFormatter extends Function<Throwable, String> {}

    private static class InfoFormatter implements ExceptionFormatter {
        @Override
        public String apply(Throwable th) {
            return assemblyUserMessage(th);
        }

        private @NotNull String assemblyUserMessage(Throwable th) {
            return "Произошла ошибка: " +
                    (th.getLocalizedMessage() == null || th.getLocalizedMessage().isEmpty() ?
                            "неизвестная ошибка" : th.getLocalizedMessage()) +
                    ". Вы можете ПОПРОБОВАТЬ снова или ОБРАТИТЬСЯ к администратору бота";
        }
    }

    private static class DebugFormatter extends InfoFormatter {
        @Override
        public @NotNull String apply(Throwable th) {
            return super.apply(th) +
                    Strings.repeat(System.lineSeparator(), 2) +
                    assemblyDeveloperMessage(th);
        }

        private @NotNull String assemblyDeveloperMessage(@NotNull Throwable th) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            th.printStackTrace(new PrintStream(out));
            String stackTraceInString = out.toString();

            return "Информация ДЛЯ РАЗРАБОТЧИКОВ:" +
                    System.lineSeparator() +
                    stackTraceInString;
        }
    }

    public static class Formatters {
        @Contract(value = " -> new", pure = true)
        public static @NotNull ExceptionFormatter info() {
            return new InfoFormatter();
        }

        @Contract(value = " -> new", pure = true)
        public static @NotNull ExceptionFormatter debug() {
            return new DebugFormatter();
        }
    }
}
