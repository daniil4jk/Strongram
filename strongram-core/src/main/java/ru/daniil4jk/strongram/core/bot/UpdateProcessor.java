package ru.daniil4jk.strongram.core.bot;

import org.jetbrains.annotations.Nullable;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.core.response.sender.ResponseSink;

import java.util.function.BiConsumer;

public interface UpdateProcessor extends BiConsumer<Update, @Nullable ResponseSink> {
    void setDefaultCallback(ResponseSink callback);
}
