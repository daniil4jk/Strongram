package ru.daniil4jk.strongram.handler;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.context.BotContext;
import ru.daniil4jk.strongram.dialog.CannotProcessCaseException;

/**
 * They form a Chain of Responsibility through which all
 * Updates that are not processed by user-specific handlers pass.
 */
public interface UpdateHandler {
    void setNext(UpdateHandler handler);
    BotApiMethod<?> process(Update update, BotContext context) throws CannotProcessCaseException;

    @NotNull
    @Contract(value = " -> new", pure = true)
    static ChainBuilder chainBuilder() {
        return new ChainBuilder();
    }

    class ChainBuilder {
        private UpdateHandler first;
        private UpdateHandler last;
        private boolean init = false;

        private ChainBuilder() {
        }

        private void init(UpdateHandler handler) {
            first = handler;
            last = handler;
            init = true;
        }

        public ChainBuilder beforeAll(UpdateHandler handler) {
            if (!init) {
                init(handler);
                return this;
            }

            handler.setNext(first);
            first = handler;
            return this;
        }

        public ChainBuilder afterAll(UpdateHandler handler) {
            if (!init) {
                init(handler);
                return this;
            }

            last.setNext(handler);
            last = handler;
            return this;
        }

        public UpdateHandler build() {
            return first;
        }
    }
}
