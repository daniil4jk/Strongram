package ru.daniil4jk.strongram.core.context.request;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.daniil4jk.strongram.core.bot.Bot;
import ru.daniil4jk.strongram.core.context.storage.Storage;
import ru.daniil4jk.strongram.core.unboxer.Unboxer;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ManagedRequestContext implements RequestContext {
    private final ActiveMonitor monitor = new ActiveMonitor();
    private final RequestContext inherit;

    public ManagedRequestContext(RequestContext context) {
        this.inherit = context;
    }

    @Override
    public List<PartialBotApiMethod<?>> getResponses() {
        monitor.check();
        return inherit.getResponses();
    }

    @Override
    public void respond(PartialBotApiMethod<?> response) {
        monitor.check();
        inherit.respond(response);
    }

    @Override
    public void respond(String text) {
        monitor.check();
        inherit.respond(text);
    }

    @Override
    public void respond(String text, ReplyKeyboard keyboard) {
        monitor.check();
        inherit.respond(text, keyboard);
    }

    @Override
    public void respond(String text, File file, MediaType type) {
        monitor.check();
        inherit.respond(text, file, type);
    }

    @Override
    public TelegramUUID getUUID() {
        monitor.check();
        return inherit.getUUID();
    }

    @Override
    public Update getRequest() {
        monitor.check();
        return inherit.getRequest();
    }

    @Override
    public <T> T getRequest(@NotNull Unboxer<T> unboxer) {
        monitor.check();
        return inherit.getRequest(unboxer);
    }

    @Override
    public Storage getStorage() {
        monitor.check();
        return inherit.getStorage();
    }

    @Override
    public Bot getBot() {
        monitor.check();
        return inherit.getBot();
    }

    public void deactivate() {
        monitor.deactivate();
    }

    private static class ActiveMonitor {
        private final AtomicBoolean isActive = new AtomicBoolean(true);

        public void deactivate() {
            isActive.set(false);
        }

        public void check() {
            if (!isActive.get()) {
                throw new IllegalStateException("Невозможно обратиться к контексту, он истёк");
            }
        }
    }
}
