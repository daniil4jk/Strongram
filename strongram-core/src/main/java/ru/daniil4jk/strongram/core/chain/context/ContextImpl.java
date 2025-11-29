package ru.daniil4jk.strongram.core.chain.context;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.daniil4jk.strongram.core.bot.Bot;
import ru.daniil4jk.strongram.core.bot.BotCredentials;
import ru.daniil4jk.strongram.core.chain.caster.Caster;
import ru.daniil4jk.strongram.core.dto.TelegramUUID;
import ru.daniil4jk.strongram.core.parser.TelegramObjectParseException;
import ru.daniil4jk.strongram.core.parser.to.uuid.TelegramUUIDFromAnyParserService;
import ru.daniil4jk.strongram.core.util.Lazy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContextImpl implements Context {
    private final Lazy<List<BotApiMethod<?>>> responses = new Lazy<>(ArrayList::new);
    private final RequestState state = new RequestStateImpl();

    private final Bot bot;
    private final Update update;
    private final Lazy<TelegramUUID> uuid;

    public ContextImpl(Bot bot, Update update) {
        this.bot = bot;
        this.update = update;

        uuid = new Lazy<>(
                () -> {
                    try {
                        return TelegramUUIDFromAnyParserService.getInstance().parse(update);
                    } catch (TelegramObjectParseException e) {
                        return null;
                    }
                }
        );
    }

    @Override
    public List<BotApiMethod<?>> getResponsesList() {
        if (!responses.isInitialized()) return Collections.emptyList();
        return responses.get();
    }

    @Override
    public void respond(BotApiMethod<?> response) {
        responses.initOrGet().add(response);
    }

    @Override
    public void respond(String text) {
        responses.initOrGet().add(
                SendMessage.builder()
                        .chatId(getUserId().getChatId())
                        .text(text)
                        .build()
        );
    }

    @Override
    public TelegramUUID getUserId() {
        return uuid.initOrGet();
    }

    @Override
    public Update getRequest() {
        return update;
    }

    @Override
    public <T> T getRequestAs(@NotNull Caster<T> caster) {
        return caster.apply(update);
    }

    @Override
    public RequestState getRequestState() {
        return state;
    }

    @Override
    public TelegramClient getClient() {
        return bot.getClient();
    }

    @Override
    public BotCredentials getCredentials() {
        return bot.getCredentials();
    }
}
