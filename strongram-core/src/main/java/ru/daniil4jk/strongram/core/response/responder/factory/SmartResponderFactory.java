package ru.daniil4jk.strongram.core.response.responder.factory;

import ru.daniil4jk.strongram.core.response.responder.smart.SmartResponder;

public interface SmartResponderFactory extends ResponserFactory {
    SmartResponder createSmart();
}
