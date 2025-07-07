package ru.daniil4jk.strongram.handler.permanent;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.context.BotContext;

/**
 * They form a Chain of Responsibility through which all
 * Updates that are not processed by user-specific handlers pass.
 */
public interface PermanentHandler {
    void setNext(PermanentHandler handler);
    BotApiMethod<?> process(Update update, BotContext context);

    @NotNull
    @Contract(value = " -> new", pure = true)
    static ChainBuilder chainBuilder() {
        return new ChainBuilder();
    }

    class ChainBuilder {
        private PermanentHandler first;
        private PermanentHandler last;
        private boolean init = false;

        private ChainBuilder() {}

        private void init (PermanentHandler handler) {
            first = handler;
            last = handler;
            init = true;
        }

        public ChainBuilder beforeAll(PermanentHandler handler) {
            if (!init) {
                init(handler);
                return this;
            }

            handler.setNext(first);
            first = handler;
            return this;
        }

        public ChainBuilder afterAll(PermanentHandler handler) {
            if (!init) {
                init(handler);
                return this;
            }

            last.setNext(handler);
            last = handler;
            return this;
        }

        public PermanentHandler build() {
            return first;
        }
    }
}
