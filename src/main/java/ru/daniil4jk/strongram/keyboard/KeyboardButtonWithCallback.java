package ru.daniil4jk.strongram.keyboard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.*;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import ru.daniil4jk.strongram.context.BotContext;
import ru.daniil4jk.strongram.handler.permanent.KeyboardCallbackPermanentHandler;

import java.util.function.Consumer;

@Slf4j
@Getter
@Setter
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class KeyboardButtonWithCallback extends KeyboardButton implements ButtonWithCallback {
    @JsonIgnore
    private Runnable callback;
    @JsonIgnore
    private Consumer<Exception> onException;
    @JsonIgnore
    private boolean removeOnException;

    {
        BotContext.getByClass(KeyboardCallbackPermanentHandler.class).addButton(this);
    }

    public KeyboardButtonWithCallback(@NonNull String text) {
        this(text, () -> {});
    }

    public KeyboardButtonWithCallback(@NonNull String text, Runnable callback) {
        this(text, callback, e -> {
            log.error(e.getLocalizedMessage(), e);
            }, true);
    }

    public KeyboardButtonWithCallback(@NonNull String text, Runnable callback,
                                      Consumer<Exception> onException, boolean removeOnException) {
        super(text);
        this.callback = callback;
        this.onException = onException;
        this.removeOnException = removeOnException;
    }

    public KeyboardButtonWithCallback(@NonNull String text, Boolean requestContact,
                                      Boolean requestLocation, KeyboardButtonPollType requestPoll,
                                      WebAppInfo webApp, KeyboardButtonRequestUser requestUser,
                                      KeyboardButtonRequestChat requestChat,
                                      KeyboardButtonRequestUsers requestUsers,
                                      Runnable callback, Consumer<Exception> onException,
                                      boolean removeOnException) {
        super(text, requestContact, requestLocation, requestPoll, webApp,
                requestUser, requestChat, requestUsers);
        this.callback = callback;
        this.onException = onException;
        this.removeOnException = removeOnException;
    }

    @Override
    public void callback() {
        callback.run();
    }

    @Override
    public String getCallbackId() {
        return getText();
    }

    @Override
    public void onException(Exception e) {
        onException.accept(e);
    }

    @Override
    public boolean isRemoveOnException() {
        return removeOnException;
    }
}
