package ru.daniil4jk.simple_tg_lib.handler.permanent;

import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Setter
public abstract class AbstractPermanentHandler implements PermanentHandler{
    private PermanentHandler next;

    protected void processNext(AbsSender absSender, Update update) {
        next.process(absSender, update);
    }
}
