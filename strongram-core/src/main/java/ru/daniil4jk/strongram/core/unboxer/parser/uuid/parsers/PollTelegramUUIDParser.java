package ru.daniil4jk.strongram.core.unboxer.parser.uuid.parsers;

import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import ru.daniil4jk.strongram.core.context.request.TelegramUUID;
import ru.daniil4jk.strongram.core.unboxer.parser.TelegramObjectParseException;


public class PollTelegramUUIDParser extends TelegramUUIDParser<Poll> {
    @Override
    public Class<Poll> getInputClass() {
        return Poll.class;
    }

    @Override
    public TelegramUUID parse(Poll t) throws TelegramObjectParseException {
        throw new TelegramObjectParseException("%s has`nt TelegramUUID payload"
                .formatted(t.getClass().getName()));
    }
}
