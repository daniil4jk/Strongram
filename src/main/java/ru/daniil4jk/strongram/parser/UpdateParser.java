package ru.daniil4jk.strongram.parser;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class UpdateParser<O> implements Parser<Update, O> {

    @Override
    public Class<Update> getParsingClass() {
        return Update.class;
    }

    @Override
    public O parse(Update update) {
        if (update.hasMessage()) return parseInternal(update.getMessage());
        if (update.hasInlineQuery()) return parseInternal(update.getInlineQuery());
        if (update.hasChosenInlineQuery()) return parseInternal(update.getChosenInlineQuery());
        if (update.hasCallbackQuery()) return parseInternal(update.getCallbackQuery());
        if (update.hasChannelPost()) return parseInternal(update.getChannelPost());
        if (update.hasShippingQuery()) return parseInternal(update.getShippingQuery());
        if (update.hasPreCheckoutQuery()) return parseInternal(update.getPreCheckoutQuery());
        if (update.hasPoll()) return parseInternal(update.getPoll());
        if (update.hasPollAnswer()) return parseInternal(update.getPollAnswer());
        if (update.hasMyChatMember()) return parseInternal(update.getMyChatMember());
        if (update.hasChatMember()) return parseInternal(update.getChatMember());
        if (update.hasChatJoinRequest()) return parseInternal(update.getChatJoinRequest());
        if (update.hasBusinessConnection()) return parseInternal(update.getBusinessConnection());
        if (update.hasBusinessMessage()) return parseInternal(update.getBusinessMessage());
        if (update.hasPaidMediaPurchased()) return parseInternal(update.getPaidMediaPurchased());

        if (update.hasEditedChannelPost()) return parseInternal(update.getEditedChannelPost());
        if (update.hasEditedMessage()) return parseInternal(update.getEditedMessage());
        if (update.hasEditedBusinessMessage()) return parseInternal(update.getEditedBuinessMessage());
        if (update.hasDeletedBusinessMessage()) return parseInternal(update.getDeletedBusinessMessages());

        throw new TelegramObjectParseException("empty update");
    }

    protected abstract <I extends BotApiObject> O parseInternal(I t);
}
