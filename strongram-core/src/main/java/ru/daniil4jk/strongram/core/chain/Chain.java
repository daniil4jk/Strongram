package ru.daniil4jk.strongram.core.chain;

import java.util.List;

public class Chain<T extends NextConsumer<T>> {
    private final List<T> chainList;

    public Chain(ChainListCreator<T> chainListCreator) {
        this(chainListCreator.getResultAsList());
    }

    public Chain(List<T> chainList) {
        this.chainList = chainList;
    }

    public T build() {
        T previous = null;
        for (T current : chainList) {
            if (previous != null) {
                previous.setNext(current);
            }
            previous = current;
        }
        try {
            return chainList.get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
