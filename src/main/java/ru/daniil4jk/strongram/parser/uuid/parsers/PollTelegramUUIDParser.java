package ru.daniil4jk.strongram.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import ru.daniil4jk.strongram.TelegramUUID;
import ru.daniil4jk.strongram.parser.TelegramObjectParseException;


public class PollTelegramUUIDParser extends TelegramUUIDParser<Poll> {
    @Override
    public Class<Poll> getParsingClass() {
        return Poll.class;
    }

    @Override
    public TelegramUUID parse(Poll t) {
        throw new TelegramObjectParseException("poll doesn`t contains any info about user");
    }
}
