package ru.daniil4jk.strongram.core.chain.context;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.core.bot.Bot;
import ru.daniil4jk.strongram.core.chain.caster.Transformer;
import ru.daniil4jk.strongram.core.parser.TelegramObjectParseException;
import ru.daniil4jk.strongram.core.parser.uuid.TelegramUUIDParserService;
import ru.daniil4jk.strongram.core.storage.Storage;
import ru.daniil4jk.strongram.core.storage.StorageImpl;
import ru.daniil4jk.strongram.core.util.Lazy;
import ru.daniil4jk.strongram.core.util.MessageArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RequestContextImpl implements RequestContext {
    private final Lazy<List<BotApiMethod<?>>> responses = new Lazy<>(ArrayList::new);
    private final Storage storage = new StorageImpl();

    private final Bot bot;
    private final Update update;
    private final Lazy<TelegramUUID> uuid;

    public RequestContextImpl(Bot bot, Update update) {
        this.bot = bot;
        this.update = update;

        uuid = new Lazy<>(
                () -> {
                    try {
                        return TelegramUUIDParserService.getInstance().parse(update);
                    } catch (TelegramObjectParseException e) {
                        return null;
                    }
                }
        );
    }

    @Override
    public List<BotApiMethod<?>> getResponses() {
        if (!responses.isInitialized()) return Collections.emptyList();
        return responses.get();
    }

    @Override
    public void respond(BotApiMethod<?> response) {
        responses.initOrGet().add(response);
    }

    @Override
    public void respond(String text) {
        new MessageArray(text)
                .asList()
                .forEach(messageText ->
                        respond(SendMessage.builder()
                                .chatId(getUserId().getChatId())
                                .text(messageText)
                                .build())
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
    public <T> T getRequest(@NotNull Transformer<T> transformer) {
        return transformer.apply(update);
    }

    @Override
    public Storage getStorage() {
        return storage;
    }

    @Override
    public Bot getBot() {
        return bot;
    }
}
