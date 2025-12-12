package ru.daniil4jk.strongram.core.unboxer.parser.text.parser;

import org.telegram.telegrambots.meta.api.objects.polls.PollAnswer;
import ru.daniil4jk.strongram.core.unboxer.parser.TelegramObjectParseException;

public class PollAnswerTextParser extends TextParser<PollAnswer> {
    @Override
    public Class<PollAnswer> getInputClass() {
        return PollAnswer.class;
    }

    @Override
    public String parse(PollAnswer t) throws TelegramObjectParseException {
        return t.getOptionIds().toString();
    }
}
