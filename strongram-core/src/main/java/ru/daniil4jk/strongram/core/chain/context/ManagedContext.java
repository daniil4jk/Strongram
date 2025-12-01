package ru.daniil4jk.strongram.core.chain.context;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.core.bot.Bot;
import ru.daniil4jk.strongram.core.bot.BotCredentials;
import ru.daniil4jk.strongram.core.chain.caster.Transformer;
import ru.daniil4jk.strongram.core.dto.TelegramUUID;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ManagedContext extends ContextImpl {
    private final ActiveMonitor monitor = new ActiveMonitor();

    public ManagedContext(Bot bot, Update update) {
        super(bot, update);
    }

    @Override
    public List<BotApiMethod<?>> getResponses() {
        monitor.check();
        return super.getResponses();
    }

    @Override
    public void respond(BotApiMethod<?> response) {
        monitor.check();
        super.respond(response);
    }

    @Override
    public void respond(String text) {
        monitor.check();
        super.respond(text);
    }

    @Override
    public TelegramUUID getUserId() {
        monitor.check();
        return super.getUserId();
    }

    @Override
    public Update getRequest() {
        monitor.check();
        return super.getRequest();
    }

    @Override
    public <T> T getRequest(@NotNull Transformer<T> transformer) {
        monitor.check();
        return super.getRequest(transformer);
    }

    @Override
    public RequestState getState() {
        monitor.check();
        return super.getState();
    }

    @Override
    public TelegramClient getClient() {
        monitor.check();
        return super.getClient();
    }

    @Override
    public BotCredentials getCredentials() {
        monitor.check();
        return super.getCredentials();
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
