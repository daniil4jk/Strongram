package ru.daniil4jk.strongram.parser.payload.parser;

import org.telegram.telegrambots.meta.api.objects.polls.PollAnswer;
import ru.daniil4jk.strongram.parser.TelegramObjectParseException;

public class PollAnswerPayloadParser extends PayloadParser<PollAnswer> {
    @Override
    public Class<PollAnswer> getParsingClass() {
        return PollAnswer.class;
    }

    @Override
    public String parse(PollAnswer t) throws TelegramObjectParseException {
        return t.getOptionIds().toString();
    }
}
