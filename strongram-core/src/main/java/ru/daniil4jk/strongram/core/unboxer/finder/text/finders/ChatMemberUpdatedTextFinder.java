package ru.daniil4jk.strongram.core.unboxer.finder.text.finders;

import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberUpdated;
import ru.daniil4jk.strongram.core.unboxer.finder.TelegramObjectFinderException;

public class ChatMemberUpdatedTextFinder extends TextFinder<ChatMemberUpdated> {
    @Override
    public Class<ChatMemberUpdated> getInputClass() {
        return ChatMemberUpdated.class;
    }

    @Override
    public String parse(ChatMemberUpdated t) throws TelegramObjectFinderException {
        throwNotContainsStringException(t.getClass());
        return null;
    }
}
