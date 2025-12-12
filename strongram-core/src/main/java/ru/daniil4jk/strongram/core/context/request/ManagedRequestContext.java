package ru.daniil4jk.strongram.core.context.request;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.core.bot.Bot;
import ru.daniil4jk.strongram.core.context.storage.Storage;
import ru.daniil4jk.strongram.core.transformer.Transformer;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ManagedRequestContext implements RequestContext {
    private final ActiveMonitor monitor = new ActiveMonitor();
    private final RequestContext inherit;

    public ManagedRequestContext(RequestContext context) {
        this.inherit = context;
    }

    @Override
    public List<BotApiMethod<?>> getResponses() {
        return inherit.getResponses();
    }

    @Override
    public void respond(BotApiMethod<?> response) {
        monitor.check();
        inherit.respond(response);
    }

    @Override
    public void respond(String text) {
        monitor.check();
        inherit.respond(text);
    }

    @Override
    public TelegramUUID getUserId() {
        monitor.check();
        return inherit.getUserId();
    }

    @Override
    public Update getRequest() {
        monitor.check();
        return inherit.getRequest();
    }

    @Override
    public <T> T getRequest(@NotNull Transformer<T> transformer) {
        monitor.check();
        return inherit.getRequest(transformer);
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
