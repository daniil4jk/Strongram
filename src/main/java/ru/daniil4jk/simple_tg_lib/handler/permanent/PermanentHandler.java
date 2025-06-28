package ru.daniil4jk.simple_tg_lib.handler.permanent;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * They form a Chain of Responsibility through which all
 * Updates that are not processed by user-specific handlers pass.
 */
public interface PermanentHandler {
    void setNext(PermanentHandler handler);
    void process(Update update);

    //todo add Command handler and Don`tKnowWhatIsThis handler
    PermanentHandler defaultPermanentHandlerChain;
}
