package ru.daniil4jk.strongram.core.unboxer.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.polls.PollAnswer;
import ru.daniil4jk.strongram.core.context.request.TelegramUUID;
import ru.daniil4jk.strongram.core.unboxer.parser.TelegramObjectParseException;

public class PollAnswerTelegramUUIDParser extends TelegramUUIDParser<PollAnswer> {

    @Override
    public Class<PollAnswer> getInputClass() {
        return PollAnswer.class;
    }

    @Override
    public TelegramUUID parse(PollAnswer t) throws TelegramObjectParseException {
        return new TelegramUUID(t.getVoterChat(), t.getUser());
    }
}
