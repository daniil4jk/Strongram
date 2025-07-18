package ru.daniil4jk.strongram.keyboard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.*;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

@Slf4j
@Getter
@Setter
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class KeyboardButtonWithCallback extends KeyboardButton implements ButtonWithCallback {
    @JsonIgnore
    private ButtonCallbackAction callbackAction;
    @JsonIgnore
    private volatile boolean added = false;

    public KeyboardButtonWithCallback(@NonNull String text) {
        this(text, null);
    }

    public KeyboardButtonWithCallback(@NonNull String text, ButtonCallbackAction callbackAction) {
        super(text);
        this.callbackAction = callbackAction;
    }

    public KeyboardButtonWithCallback(@NonNull String text, Boolean requestContact,
                                      Boolean requestLocation, KeyboardButtonPollType requestPoll,
                                      WebAppInfo webApp, KeyboardButtonRequestUser requestUser,
                                      KeyboardButtonRequestChat requestChat,
                                      KeyboardButtonRequestUsers requestUsers,
                                      ButtonCallbackAction callbackAction) {
        super(text, requestContact, requestLocation, requestPoll,
                webApp, requestUser, requestChat, requestUsers);
        this.callbackAction = callbackAction;
    }

    public static KeyboardButtonWithCallbackBuilder<?, ?> builder() {
        return new KeyboardButtonWithCallbackBuilderImpl();
    }

    @Override
    public void prepareToAddingInRegistry() {
        added = true;
    }

    @Override
    public String getCallbackId() {
        return getText();
    }

    @Override
    public void setText(@NonNull String text) {
        if (added) {
            throw new IllegalCallerException("Changing text after adding button " +
                    "to registry will make the callbackAction unreachable");
        }
        super.setText(text);
    }

    public static abstract class KeyboardButtonWithCallbackBuilder
            <C extends KeyboardButtonWithCallback,
                    B extends KeyboardButtonWithCallbackBuilder<C, B>>
            extends KeyboardButtonBuilder<C, B> {

        @JsonIgnore
        private B added(boolean added) {
            return self();
        }
    }
}
