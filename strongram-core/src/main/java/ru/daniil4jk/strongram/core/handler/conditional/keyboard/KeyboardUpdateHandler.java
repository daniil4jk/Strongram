package ru.daniil4jk.strongram.core.handler.conditional.keyboard;

import lombok.*;
import org.apache.commons.lang3.function.TriConsumer;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.Nullable;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.daniil4jk.strongram.core.TelegramUUID;
import ru.daniil4jk.strongram.core.context.BotContext;
import ru.daniil4jk.strongram.core.handler.conditional.ConditionalUpdateHandler;
import ru.daniil4jk.strongram.core.handler.conditional.keyboard.pattern.KeyboardRegexPatternGenerator;
import ru.daniil4jk.strongram.core.handler.conditional.keyboard.registry.KeyboardSpecificButtonRegistry;
import ru.daniil4jk.strongram.core.parser.ParserService;
import ru.daniil4jk.strongram.core.parser.payload.PayloadParserService;
import ru.daniil4jk.strongram.core.parser.uuid.TelegramUUIDParserService;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public abstract class KeyboardUpdateHandler<Keyboard extends ReplyKeyboard, Button> extends ConditionalUpdateHandler {
    private static final ParserService<String> payloadParser = PayloadParserService.getInstance();
    private static final ParserService<TelegramUUID> uuidParser = TelegramUUIDParserService.getInstance();

    private final KeyboardSpecificButtonRegistry<Button> buttonRegistry;
    private final ButtonActionRegistry<Button> actionRegistry = new ButtonActionRegistryImpl<>();

    protected KeyboardUpdateHandler(Keyboard keyboard, Map<Button, ButtonAction> actions) {
        this(keyboard);
        actions.forEach(actionRegistry::addAction);
    }

    public KeyboardUpdateHandler(Keyboard keyboard) {
        super(KeyboardRegexPatternGenerator.getByKeyboard(keyboard).getPattern());
        buttonRegistry = KeyboardSpecificButtonRegistry.getByKeyboard(keyboard);
        addActions(actionRegistry, keyboard);
    }

    abstract protected void addActions(ButtonActionRegistry<Button> registry, ReplyKeyboard keyboard);

    @Override
    protected BotApiMethod<?> execute(Update update, BotContext context) {
        String payload = payloadParser.parse(update);
        TelegramUUID uuid = uuidParser.parse(update);

        BiFunction<TelegramUUID, String, BotApiMethod<?>> action = actionRegistry.getAction(
                buttonRegistry.getButtonByPayload(payload)
        );

        return action.apply(uuid, payload);
    }

    public interface ButtonAction extends BiFunction<TelegramUUID, String, BotApiMethod<?>> {
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode(callSuper = true)
    public static class Functional<Keyboard extends ReplyKeyboard, Button> extends
            KeyboardUpdateHandler<Keyboard, Button> {

        private static final ReplyKeyboard PLUG = ReplyKeyboardMarkup.builder().build();
        private static final KeyboardUpdateHandler<?, ?> DEFAULT_HANDLER = new KeyboardUpdateHandler<>(PLUG) {
            @Override
            protected void addActions(ButtonActionRegistry<Object> registry, ReplyKeyboard keyboard) {

            }
        };

        private final BiConsumer<Update, BotContext> beforeExecute;
        private final BiConsumer<Update, BotContext> beforeProcessNext;
        private final TriFunction<Update, BotContext, Exception, BotApiMethod<?>> onException;
        private final TriConsumer<Update, BotContext, BotApiMethod<?>> afterExecute;

        public Functional(Keyboard keyboard, Map<Button, ButtonAction> actions) {
            this(
                    keyboard,
                    actions,
                    DEFAULT_HANDLER::beforeExecute,
                    DEFAULT_HANDLER::beforeProcessNext,
                    DEFAULT_HANDLER::onException,
                    DEFAULT_HANDLER::afterExecute
            );
        }

        @Builder
        public Functional(Keyboard keyboard,
                          @Singular Map<Button, ButtonAction> actions,
                          BiConsumer<Update, BotContext> beforeExecute,
                          BiConsumer<Update, BotContext> beforeProcessNext,
                          TriFunction<Update, BotContext, Exception, BotApiMethod<?>> onException,
                          TriConsumer<Update, BotContext, BotApiMethod<?>> afterExecute) {
            super(keyboard, actions);
            this.beforeExecute = Optional.ofNullable(beforeExecute).orElse(DEFAULT_HANDLER::beforeExecute);
            this.beforeProcessNext = Optional.ofNullable(beforeProcessNext).orElse(DEFAULT_HANDLER::beforeProcessNext);
            this.onException = Optional.ofNullable(onException).orElse(DEFAULT_HANDLER::onException);
            this.afterExecute = Optional.ofNullable(afterExecute).orElse(DEFAULT_HANDLER::afterExecute);
        }

        @Override
        protected void beforeExecute(Update update, BotContext context) {
            beforeExecute.accept(update, context);
        }

        @Override
        protected void beforeProcessNext(Update update, BotContext context) {
            beforeProcessNext.accept(update, context);
        }

        @Override
        protected BotApiMethod<?> onException(Update update, BotContext context, Exception e) {
            return onException.apply(update, context, e);
        }

        @Override
        protected void afterExecute(Update update, BotContext context, @Nullable BotApiMethod<?> result) {
            afterExecute.accept(update, context, result);
        }

        @Override
        protected void addActions(ButtonActionRegistry<Button> registry, ReplyKeyboard keyboard) {
            //do nothing, because all actions added in constructor
        }
    }
}