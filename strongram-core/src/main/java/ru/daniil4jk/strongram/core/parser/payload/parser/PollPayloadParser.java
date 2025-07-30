package ru.daniil4jk.strongram.core.parser.payload.parser;

import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import ru.daniil4jk.strongram.core.parser.TelegramObjectParseException;

public class PollPayloadParser extends PayloadParser<Poll> {
    @Override
    public Class<Poll> getInputClass() {
        return Poll.class;
    }

    @Override
    public String parse(Poll t) throws TelegramObjectParseException {
        return t.getQuestion();
    }
}
