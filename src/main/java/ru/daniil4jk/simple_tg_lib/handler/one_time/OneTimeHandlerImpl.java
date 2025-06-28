package ru.daniil4jk.simple_tg_lib.handler.one_time;

import lombok.Builder;
import org.apache.commons.lang3.ArrayUtils;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.simple_tg_lib.UpdateEntriesDetector;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Builder
public class OneTimeHandlerImpl implements OneTimeHandler {
    private final Supplier<BotApiMethodMessage> firstNotification;
    private final Supplier<BotApiMethodMessage> notification;
    private final Predicate<Update> canProgress;
    private final Consumer<Update> progress;
    private final Consumer<Exception> onException;
    private final boolean removeOnException;

    @Override
    public BotApiMethodMessage firstNotification() {
        return firstNotification.get();
    }

    @Override
    public BotApiMethodMessage notification() {
        return notification.get();
    }

    @Override
    public boolean canProcess(Update update) {
        return canProgress.test(update);
    }

    @Override
    public void process(Update update) {
        progress.accept(update);
    }

    @Override
    public void onException(Exception e) {
        onException.accept(e);
    }

    @Override
    public boolean removeOnException() {
        return removeOnException;
    }

    private static class CanProgressByType implements Predicate<Update> {
        private static final UpdateEntriesDetector DEFAULT_UPDATE_ENTRIES_DETECTOR = new UpdateEntriesDetector();

        public static CanProgressByType of(Class<? extends BotApiObject>... supportedClasses) {
            return new CanProgressByType(DEFAULT_UPDATE_ENTRIES_DETECTOR, supportedClasses);
        }

        private final UpdateEntriesDetector updateEntriesDetector;
        private final Class<? extends BotApiObject>[] supportedClasses;

        public CanProgressByType(UpdateEntriesDetector updateEntriesDetector,
                                 Class<? extends BotApiObject>... supportedClasses) {
            this.updateEntriesDetector = updateEntriesDetector;

            if (supportedClasses.length == 0) {
                throw new IllegalArgumentException("supportedClasses can`t be empty!");
            }
            this.supportedClasses = supportedClasses;
        }

        @Override
        public boolean test(Update update) {
            return ArrayUtils.contains(supportedClasses, updateEntriesDetector.getEntryClass(update));
        }
    }
}
