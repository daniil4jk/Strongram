package ru.daniil4jk.simple_tg_lib;

import org.apache.commons.lang3.tuple.Pair;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.inlinequery.ChosenInlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import org.telegram.telegrambots.meta.api.objects.payments.ShippingQuery;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.api.objects.polls.PollAnswer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

public class UpdateEntriesDetector {
    private List<Pair<Predicate<Update>, Class<? extends BotApiObject>>> checkers = new ArrayList<>();
    private List<Pair<Predicate<Update>, Class<? extends BotApiObject>>> editedCheckers = new ArrayList<>();
    private final AtomicBoolean enableEdited = new AtomicBoolean();

    {
        add(Update::hasMessage, Message.class);
        add(Update::hasInlineQuery, InlineQuery.class);
        add(Update::hasChosenInlineQuery, ChosenInlineQuery.class);
        add(Update::hasCallbackQuery, CallbackQuery.class);
        add(Update::hasChannelPost, Message.class);
        add(Update::hasShippingQuery, ShippingQuery.class);
        add(Update::hasPreCheckoutQuery, PreCheckoutQuery.class);
        add(Update::hasPoll, Poll.class);
        add(Update::hasPollAnswer, PollAnswer.class);
        add(Update::hasMyChatMember, ChatMemberUpdated.class);
        add(Update::hasChatMember, ChatMemberUpdated.class);
        add(Update::hasChatJoinRequest, ChatJoinRequest.class);

        addEdited(Update::hasEditedMessage, Message.class);
        addEdited(Update::hasEditedChannelPost, Message.class);
    }

    private void add(Predicate<Update> predicate, Class<? extends BotApiObject> clazz) {
        checkers.add(Pair.of(predicate, clazz));
    }

    private void addEdited(Predicate<Update> predicate, Class<? extends BotApiObject> clazz) {
        editedCheckers.add(Pair.of(predicate, clazz));
    }

    public void enableEdited(boolean enableEdited) {
        this.enableEdited.set(enableEdited);
    }

    public Class<? extends BotApiObject> getEntryClass(Update update) {
        for (var checker : checkers) {
            if (checker.getLeft().test(update)) return checker.getRight();
        }
        if (enableEdited.get()) {
            for (var checker : editedCheckers) {
                if (checker.getLeft().test(update)) return checker.getRight();
            }
        }
        return null;
    }
}
