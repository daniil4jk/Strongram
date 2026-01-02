package ru.daniil4jk.strongram.core.context.request;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.core.bot.Bot;
import ru.daniil4jk.strongram.core.context.storage.InMemoryStorage;
import ru.daniil4jk.strongram.core.context.storage.Storage;
import ru.daniil4jk.strongram.core.response.sender.accumulating.AccumulatingSender;
import ru.daniil4jk.strongram.core.response.sender.smart.SmartSender;
import ru.daniil4jk.strongram.core.response.sender.smart.SmartSenderImpl;
import ru.daniil4jk.strongram.core.unboxer.Unboxer;
import ru.daniil4jk.strongram.core.unboxer.finder.uuid.TelegramUUIDFinderService;

public class RequestContextImpl implements RequestContext {
    private final Storage storage = new InMemoryStorage();
    private final Bot bot;
    private final Update update;
    private final TelegramUUID uuid;
    private final SmartSender sender;

    public RequestContextImpl(Bot bot, Update update, AccumulatingSender baseSender) {
        this.bot = bot;
        this.update = update;
        this.uuid = TelegramUUIDFinderService.getInstance().findIn(update);
        this.sender = new SmartSenderImpl(uuid, baseSender);
    }

    @Override
    public TelegramUUID getUUID() {
        return uuid;
    }

    @Override
    public Update getRequest() {
        return update;
    }

    @Override
    public <T> T getRequest(Unboxer<T> unboxer) {
        return unboxer.apply(update);
    }

    @Override
    public Storage getRequestScopeStorage() {
        return storage;
    }

    @Override
    public String getBotUsername() {
        return bot.getUsername();
    }

    @Override
    public SmartSender getSender() {
        return sender;
    }
}
