package ru.daniil4jk.strongram.core.chain.configurable;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.daniil4jk.strongram.core.chain.ChainListCreator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@ToString
@EqualsAndHashCode
public class ChainConfigurator<T> implements ChainListCreator<T> {
    private final List<T> chain = new ArrayList<>();

    public ChainConfigurator<T> add(T handler) {
        this.chain.add(handler);
        return this;
    }


    public ChainConfigurator<T> addBefore(Class<? extends T> beforeWhat, T toAdd) {
        addConditionalInternal(0, beforeWhat, toAdd);
        return this;
    }


    public ChainConfigurator<T> addAfter(Class<? extends T> afterWhat, T toAdd) {
        addConditionalInternal(1, afterWhat, toAdd);
        return this;
    }

    private void addConditionalInternal(
        int padding,
        Class<? extends T> finding,
        T toAdd
    ) {
        int i = 0;
        for (
                Iterator<T> iterator = chain.iterator();
                iterator.hasNext();
                i++
        ) {
            T exist = iterator.next();
            if (finding.isInstance(exist)) {
                chain.add(i + padding, toAdd);
                break;
            }
        }
    }

    public List<T> getResultAsList() {
        return chain;
    }
}
