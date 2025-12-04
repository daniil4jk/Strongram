package ru.daniil4jk.strongram.core.dialog;

import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import ru.daniil4jk.strongram.core.chain.context.RequestContext;
import ru.daniil4jk.strongram.core.dialog.state.DialogContext;
import ru.daniil4jk.strongram.core.dialog.state.DialogContextImpl;

import java.util.EnumMap;
import java.util.Map;

public class DialogImpl<ENUM extends Enum<ENUM>> implements Dialog {
    private final Map<ENUM, DialogPart<ENUM>> parts;
    @Getter(AccessLevel.PROTECTED)
    private final DialogContext<ENUM> dialogCtx;

    public DialogImpl(@NotNull ENUM initState, Map<ENUM, DialogPart<ENUM>> parts) {
        this(initState);
        this.parts.putAll(parts);
    }

    public DialogImpl(@NotNull ENUM initState) {
        this.parts = new EnumMap<>(initState.getDeclaringClass());
        this.dialogCtx = new DialogContextImpl<>(initState);
    }

    public void add(ENUM state, DialogPart<ENUM> part) {
        parts.put(state, part);
    }

    @Override
    public synchronized void accept(RequestContext ctx) {
        getCurrentPart().accept(ctx);
    }

    @Override
    public BotApiMethod<?> firstAsk() {
        return getCurrentPart().firstAsk();
    }

    @Override
    public BotApiMethod<?> repeatAsk() {
        return getCurrentPart().repeatAsk();
    }

    @Override
    public boolean isStopped() {
        return dialogCtx.isStopped();
    }

    @Override
    public boolean canAccept(RequestContext ctx) {
        return getCurrentPart().canAccept(ctx);
    }

    private DialogPart<ENUM> getCurrentPart() {
        return parts.get(dialogCtx.getState());
    }
}