package ru.daniil4jk.strongram.keyboard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.LoginUrl;
import org.telegram.telegrambots.meta.api.objects.games.CallbackGame;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.CopyTextButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.SwitchInlineQueryChosenChat;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.UUID;

@Slf4j
@Getter
@Setter
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class InlineKeyboardButtonWithCallback extends InlineKeyboardButton implements ButtonWithCallback {
    @JsonIgnore
    private ButtonCallbackAction callback;
    @JsonIgnore
    @Builder.Default
    private volatile boolean added = false;

    public InlineKeyboardButtonWithCallback(@NonNull String text) {
        this(text, (ButtonCallbackAction) null);
    }

    public InlineKeyboardButtonWithCallback(@NonNull String text, ButtonCallbackAction callback) {
        super(text);
        this.callback = callback;
    }

    public InlineKeyboardButtonWithCallback(@NonNull String text, @NonNull String callbackData) {
        this(text, callbackData, null);
    }

    public InlineKeyboardButtonWithCallback(@NonNull String text, @NonNull String callbackData,
                                            ButtonCallbackAction callback) {
        super(text);
        setCallbackData(callbackData);
        this.callback = callback;
    }

    public InlineKeyboardButtonWithCallback(@NonNull String text, String url,
                                            String callbackData, CallbackGame callbackGame,
                                            String switchInlineQuery, String switchInlineQueryCurrentChat,
                                            Boolean pay, LoginUrl loginUrl, WebAppInfo webApp,
                                            SwitchInlineQueryChosenChat switchInlineQueryChosenChat,
                                            CopyTextButton copyText, ButtonCallbackAction callback) {
        super(text, url, callbackData, callbackGame, switchInlineQuery,
                switchInlineQueryCurrentChat, pay, loginUrl, webApp,
                switchInlineQueryChosenChat, copyText);
        this.callback = callback;
    }

    public static InlineKeyboardButtonWithCallbackBuilder<?, ?> builder() {
        return new InlineKeyboardButtonWithCallbackBuilderImpl();
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
    public ButtonCallbackAction getCallbackAction() {
        return null;
    }

    @Override
    public void setCallbackData(String callbackData) {
        if (added) {
            throw new IllegalCallerException("callbackData was automatically set before adding " +
                    "the button to the registry. Changing it will make the callbackAction unreachable");
        }
        super.setCallbackData(callbackData);
    }

    public static abstract class InlineKeyboardButtonWithCallbackBuilder
            <C extends InlineKeyboardButtonWithCallback,
                    B extends InlineKeyboardButtonWithCallbackBuilder<C, B>>
            extends InlineKeyboardButtonBuilder<C, B> {

        @JsonIgnore
        private B added(boolean added) {
            return self();
        }
    }
}