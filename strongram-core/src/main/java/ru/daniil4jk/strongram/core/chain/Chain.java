package ru.daniil4jk.strongram.core.chain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.daniil4jk.strongram.core.upstream.UpstreamHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a chain of {@link NextConsumer} objects that can be linked together to form a pipeline
 * of processing steps. This class provides methods to build the chain by adding handlers
 * either sequentially or conditionally based on the presence of other handlers.
 * <p>
 * The chain is constructed in a fluent manner, allowing multiple configuration calls to be
 * chained together. Once fully configured, the {@link #build()} method links all handlers
 * together by setting the appropriate next handler in the chain and returns the first
 * handler in the sequence.
 * </p>
 */
@ToString
@EqualsAndHashCode
public class Chain<T extends NextConsumer<T>> {

    private final List<T> chain = new ArrayList<>();

    /**
     * Adds a handler to the end of the chain.
     *
     * @param handler the handler to add; must not be null
     * @return this {@link Chain} instance to allow method chaining
     */
    public Chain add(T handler) {
        this.chain.add(handler);
        return this;
    }

    /**
     * Adds a handler before the first occurrence of a handler of the specified type.
     * If no such handler is found, the new handler is not added.
     *
     * @param beforeWhat the class of the handler before which to insert; must not be null
     * @param toAdd the handler to insert; must not be null
     * @return this {@link Chain} instance to allow method chaining
     */
    public Chain addBefore(Class<? extends T> beforeWhat, T toAdd) {
        addConditionalInternal(0, beforeWhat, toAdd);
        return this;
    }

    /**
     * Adds a handler after the first occurrence of a handler of the specified type.
     * If no such handler is found, the new handler is not added.
     *
     * @param afterWhat the class of the handler after which to insert; must not be null
     * @param toAdd the handler to insert; must not be null
     * @return this {@link Chain} instance to allow method chaining
     */
    public Chain addAfter(Class<? extends T> afterWhat, T toAdd) {
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
            UpstreamHandler exist = iterator.next();
            if (finding.isInstance(exist)) {
                chain.add(i + padding, toAdd);
                break;
            }
        }
    }

    /**
     * Constructs the chain by linking each handler to the next one in sequence.
     * After this method is called, the handlers are interconnected via their
     * {@link NextConsumer#setNext(NextConsumer)} method, forming a linked chain.
     *
     * @return the first {@link NextConsumer} in the chain, which serves as the entry point
     *         for processing; or {@code null} if the chain is empty
     */
    public T build() {
        T previous = null;
        for (T current : chain) {
            if (previous != null) {
                previous.setNext(current);
            }
            previous = current;
        }
        try {
            return chain.get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
