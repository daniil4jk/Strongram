package ru.daniil4jk.strongram.core.chain;

import ru.daniil4jk.strongram.core.chain.handler.Handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Chain {
    private final List<Handler> chain = new ArrayList<>();

    public Chain add(Handler handler) {
        this.chain.add(handler);
        return this;
    }

    public Chain addBefore(Class<? extends Handler> beforeWhat, Handler toAdd) {
        addConditionalInternal(0, beforeWhat, toAdd);
        return this;
    }

    public Chain addAfter(Class<? extends Handler> afterWhat, Handler toAdd) {
        addConditionalInternal(1, afterWhat, toAdd);
        return this;
    }

    private void addConditionalInternal(int padding, Class<? extends Handler> finding, Handler toAdd) {
        int i = 0;
        for (Iterator<Handler> iterator = chain.iterator(); iterator.hasNext(); i++) {
            Handler exist = iterator.next();
            if (exist.getClass().isInstance(finding)) {
                chain.add(i + padding, toAdd);
                break;
            }
        }
    }

    public Handler build() {
        Handler previous = null;
        for (Handler current : chain) {
            if (previous != null) {
                previous.setNext(current);
            }
            previous = current;
        }
        return chain.get(0);
    }
}
