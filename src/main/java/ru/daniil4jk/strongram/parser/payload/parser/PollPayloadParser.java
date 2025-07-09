package ru.daniil4jk.strongram.parser.payload.parser;

import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import ru.daniil4jk.strongram.parser.TelegramObjectParseException;

public class PollPayloadParser extends PayloadParser<Poll> {
    @Override
    public Class<Poll> getParsingClass() {
        return Poll.class;
    }

    @Override
    public String parse(Poll t) throws TelegramObjectParseException {
        return t.getQuestion();
    }
}
