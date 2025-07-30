package ru.daniil4jk.strongram.core.handler.defaults;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.daniil4jk.strongram.core.TelegramUUID;
import ru.daniil4jk.strongram.core.context.BotContext;
import ru.daniil4jk.strongram.core.handler.AbstractUpdateHandler;
import ru.daniil4jk.strongram.core.parser.ParserService;
import ru.daniil4jk.strongram.core.parser.payload.PayloadParserService;
import ru.daniil4jk.strongram.core.parser.uuid.TelegramUUIDParserService;

import java.util.function.Function;

public class EchoUpdateHandler extends AbstractUpdateHandler {
    private static final ParserService<TelegramUUID> uuidParser = TelegramUUIDParserService.getInstance();
    private static final ParserService<String> payloadParser = PayloadParserService.getInstance();
    private final Function<String, String> responseGenerator;

    public EchoUpdateHandler() {
        this.responseGenerator = s -> s;
    }

    public EchoUpdateHandler(String response) {
        this.responseGenerator = s -> response;
    }

    public EchoUpdateHandler(Function<String, String> responseGenerator) {
        this.responseGenerator = responseGenerator;
    }

    @Override
    protected BotApiMethod<?> execute(Update update, BotContext context) {
        return new SendMessage(
              uuidParser.parse(update).getChatId().toString(),
              responseGenerator.apply(payloadParser.parse(update))
        );
    }
}
