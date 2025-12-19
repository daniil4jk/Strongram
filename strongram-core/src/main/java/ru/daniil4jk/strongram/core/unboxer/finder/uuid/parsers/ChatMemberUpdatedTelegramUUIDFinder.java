package ru.daniil4jk.strongram.core.unboxer.finder.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberUpdated;
import ru.daniil4jk.strongram.core.context.request.TelegramUUID;
import ru.daniil4jk.strongram.core.unboxer.finder.TelegramObjectFinderException;

public class ChatMemberUpdatedTelegramUUIDFinder extends TelegramUUIDFinder<ChatMemberUpdated> {
    @Override
    public Class<ChatMemberUpdated> getInputClass() {
        return ChatMemberUpdated.class;
    }

    @Override
    public TelegramUUID parse(ChatMemberUpdated t) throws TelegramObjectFinderException {
        return new TelegramUUID(t.getChat(), t.getFrom());
    }
}
