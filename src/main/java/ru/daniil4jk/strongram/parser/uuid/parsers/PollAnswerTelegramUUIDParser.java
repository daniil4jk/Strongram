package ru.daniil4jk.strongram.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.polls.PollAnswer;
import ru.daniil4jk.strongram.TelegramUUID;

public class PollAnswerTelegramUUIDParser extends TelegramUUIDParser<PollAnswer> {

    @Override
    public Class<PollAnswer> getParsingClass() {
        return PollAnswer.class;
    }

    @Override
    public TelegramUUID parse(PollAnswer t) {
        return new TelegramUUID(t.getVoterChat(), t.getUser());
    }
}
