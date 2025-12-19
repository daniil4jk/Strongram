package ru.daniil4jk.strongram.core.context.request;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.botapimethods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaBotMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.daniil4jk.strongram.core.bot.Bot;
import ru.daniil4jk.strongram.core.context.storage.Storage;
import ru.daniil4jk.strongram.core.context.storage.StorageImpl;
import ru.daniil4jk.strongram.core.unboxer.Unboxer;
import ru.daniil4jk.strongram.core.unboxer.parser.TelegramObjectParseException;
import ru.daniil4jk.strongram.core.unboxer.parser.uuid.TelegramUUIDParserService;
import ru.daniil4jk.strongram.core.util.Lazy;
import ru.daniil4jk.strongram.core.util.MessageArray;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RequestContextImpl implements RequestContext {
    private final Lazy<List<PartialBotApiMethod<?>>> responses = new Lazy<>(ArrayList::new);
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
    public List<PartialBotApiMethod<?>> getResponses() {
        if (!responses.isInitialized()) return Collections.emptyList();
        return responses.get();
    }

    @Override
    public void respond(PartialBotApiMethod<?> response) {
        responses.initOrGet().add(response);
    }

    @Override
    public void respond(String text) {
        new MessageArray(text)
                .asList()
                .forEach(messageText ->
                        respond(SendMessage.builder()
                                .chatId(getUUID().getReplyChatId())
                                .text(messageText)
                                .build())
                );
    }

    @Override
    public void respond(String text, ReplyKeyboard keyboard) {
        List<? extends SendMessage> messages = new MessageArray(text)
                .asList().stream()
                .map(str -> SendMessage.builder()
                                    .chatId(getUUID().getReplyChatId())
                                    .text(str)
                                    .build()
                ).toList();

        messages.get(messages.size() - 1).setReplyMarkup(keyboard);

        messages.forEach(this::respond);
    }

    @Override
    public void respond(String text, File file, MediaType type) {
        List<String> texts = new MessageArray(text).asList();
        SendMediaBotMethod<?> media = RespondFactory.toMessage(texts.remove(0), getUUID(), file, type);
        respond(media);

        texts.forEach(messageText ->
                respond(SendMessage.builder()
                        .chatId(getUUID().getReplyChatId())
                        .text(messageText)
                        .build())
        );
    }

    @Override
    public TelegramUUID getUUID() {
        return uuid.initOrGet();
    }

    @Override
    public Update getRequest() {
        return update;
    }

    @Override
    public <T> T getRequest(@NotNull Unboxer<T> unboxer) {
        return unboxer.apply(update);
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
