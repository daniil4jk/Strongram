package ru.daniil4jk.strongram.core.unboxer.finder.text.finders;

import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import ru.daniil4jk.strongram.core.unboxer.finder.TelegramObjectFinderException;

public class PollTextFinder extends TextFinder<Poll> {
    @Override
    public Class<Poll> getInputClass() {
        return Poll.class;
    }

    @Override
    public String parse(Poll t) throws TelegramObjectFinderException {
        return t.getQuestion();
    }
}
