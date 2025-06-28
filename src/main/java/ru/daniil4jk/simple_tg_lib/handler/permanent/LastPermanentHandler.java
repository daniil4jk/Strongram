package ru.daniil4jk.simple_tg_lib.handler.permanent;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.daniil4jk.simple_tg_lib.TelegramUUID;
import ru.daniil4jk.simple_tg_lib.parser.ParserService;

@Slf4j
public class LastPermanentHandler extends AbstractPermanentHandler {
    private static final String MESSAGE = "Я не знаю как обработать это сообщение. \uD83D\uDE14";

    private ParserService<TelegramUUID> UUIDParserService;

    @Override
    public void process(AbsSender absSender, Update update) {
        try {
            absSender.execute(SendMessage.builder()
                     .chatId(UUIDParserService.parse(update).getChatId())
                     .text(MESSAGE)
                    .build());
        } catch (TelegramApiException e) {
            log.warn(e.getLocalizedMessage(), e);
        }
    }
}
