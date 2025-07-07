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
import ru.daniil4jk.strongram.context.BotContext;
import ru.daniil4jk.strongram.handler.permanent.KeyboardCallbackPermanentHandler;

import java.util.UUID;
import java.util.function.Consumer;

@Slf4j
@Getter
@Setter
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class InlineKeyboardButtonWithCallback extends InlineKeyboardButton implements ButtonWithCallback {
    @JsonIgnore
    private final Runnable callback;
    @JsonIgnore
    private final Consumer<Exception> onException;
    @JsonIgnore
    private final boolean removeOnException;

    public InlineKeyboardButtonWithCallback(@NonNull String text) {
        this(text, () -> {});
    }

    public InlineKeyboardButtonWithCallback(@NonNull String text, Runnable callback) {
        this(text, callback, e -> {
            log.error(e.getLocalizedMessage(), e);
        }, true);
    }

    public InlineKeyboardButtonWithCallback(@NonNull String text, Runnable callback,
                                            Consumer<Exception> onException, boolean removeOnException) {
        super(text);
        this.callback = callback;
        this.onException = onException;
        this.removeOnException = removeOnException;
    }

    public void register(BotContext botContext){
        super.setCallbackData(UUID.randomUUID().toString());
        botContext.getByClass(ButtonWithCallbackRegistry.class)
                .add(this);
    }

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
    public void callback() {
        callback.run();
    }

    @Override
    public String getCallbackId() {
        return getCallbackData();
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
        throw new IllegalCallerException("callbackData was automatically set before adding " +
                "the button to the registry. Changing it will make the button unreachable");
    }
}
