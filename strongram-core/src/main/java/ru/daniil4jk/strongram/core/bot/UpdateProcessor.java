package ru.daniil4jk.strongram.core.bot;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.core.response.sender.accumulating.AccumulatingSender;

import java.util.function.BiConsumer;

public interface UpdateProcessor extends BiConsumer<Update, AccumulatingSender> {
}
