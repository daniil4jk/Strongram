package ru.daniil4jk.strongram.core.unboxer.parser.text.parser;

import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import ru.daniil4jk.strongram.core.unboxer.parser.TelegramObjectParseException;

public class PollTextParser extends TextParser<Poll> {
    @Override
    public Class<Poll> getInputClass() {
        return Poll.class;
    }

    @Override
    public String parse(Poll t) throws TelegramObjectParseException {
        return t.getQuestion();
    }
}
