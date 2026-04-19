package ru.daniil4jk.strongram.core.bot;

import ru.daniil4jk.strongram.core.response.responder.factory.ResponserFactory;

public interface Bot extends UpdateProcessor {
    String getUsername();
    ResponserFactory getResponderFactory();
}
