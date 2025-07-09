package ru.daniil4jk.strongram.keyboard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.LoginUrl;
import org.telegram.telegrambots.meta.api.objects.games.CallbackGame;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.CopyTextButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.SwitchInlineQueryChosenChat;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.UUID;
import java.util.function.Consumer;

@Slf4j
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class InlineKeyboardButtonWithCallback extends InlineKeyboardButton implements ButtonWithCallback {
    @JsonIgnore
    private final Runnable callback;
    @JsonIgnore
    private final Consumer<Exception> onException;
    @JsonIgnore
    private final boolean removeOnException;
    @JsonIgnore
    private volatile boolean added = false;

    public InlineKeyboardButtonWithCallback(@NonNull String text) {
        this(text, () -> {});
    }

    public InlineKeyboardButtonWithCallback(@NonNull String text, Runnable callback) {
        this(text, callback,
                e -> log.error(e.getLocalizedMessage(), e),
                true);
    }

    public InlineKeyboardButtonWithCallback(@NonNull String text,
                                            Runnable callback, Consumer<Exception> onException,
                                            boolean removeOnException) {
        super(text);
        this.callback = callback;
        this.onException = onException;
        this.removeOnException = removeOnException;
    }

    public InlineKeyboardButtonWithCallback(@NonNull String text, @NonNull String callbackData) {
        this(text, callbackData, () -> {});
    }

    public InlineKeyboardButtonWithCallback(@NonNull String text, @NonNull String callbackData,
                                            Runnable callback) {
        this(text, callbackData, callback,
                e -> log.error(e.getLocalizedMessage(), e),
                true);
    }

    public InlineKeyboardButtonWithCallback(@NonNull String text, @NonNull String callbackData,
                                            Runnable callback, Consumer<Exception> onException,
                                            boolean removeOnException) {
        super(text);
        setCallbackData(callbackData);
        this.callback = callback;
        this.onException = onException;
        this.removeOnException = removeOnException;
    }

    @Builder
    public InlineKeyboardButtonWithCallback(@NonNull String text, String url,
                                            String callbackData, CallbackGame callbackGame,
                                            String switchInlineQuery, String switchInlineQueryCurrentChat,
                                            Boolean pay, LoginUrl loginUrl, WebAppInfo webApp,
                                            SwitchInlineQueryChosenChat switchInlineQueryChosenChat,
                                            CopyTextButton copyText, Runnable callback,
                                            Consumer<Exception> onException, boolean removeOnException) {
        super(text, url, callbackData, callbackGame, switchInlineQuery,
                switchInlineQueryCurrentChat, pay, loginUrl, webApp,
                switchInlineQueryChosenChat, copyText);
        this.callback = callback;
        this.onException = onException;
        this.removeOnException = removeOnException;
    }

    @Override
    public void prepareToAddingInRegistry() {
        if (getCallbackData() == null) {
            setCallbackData(UUID.randomUUID().toString());
        }
        added = true;
    }

    @Override
    public String getCallbackId() {
        return getCallbackData();
    }

    @Override
    public void callback() {
        callback.run();
    }

    @Override
    public void onException(Exception e) {
        onException.accept(e);
    }

    @Override
    public boolean isRemoveOnException() {
        return removeOnException;
    }

    @Override
    public void setCallbackData(String callbackData) {
        if (added) {
            throw new IllegalCallerException("callbackData was automatically set before adding " +
                    "the button to the registry. Changing it will make the callback unreachable");
        }
        super.setCallbackData(callbackData);
    }
}
