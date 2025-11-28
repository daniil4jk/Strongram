package ru.daniil4jk.strongram.core.bot;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.function.Function;

public interface UpdateProcessor extends Function<Update, List<BotApiMethod<?>>> {
}
