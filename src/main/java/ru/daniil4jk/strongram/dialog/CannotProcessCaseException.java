package ru.daniil4jk.strongram.dialog;

/**
 * The exception that works as a flag indicates that the dialogPart cannot handle this case,
 * and the processing must be passed to the next dialog in the list.
 * <p>
 * If this exception is thrown, onException-like methods are not called.
 */
public class CannotProcessCaseException extends RuntimeException {
    public CannotProcessCaseException() {
        super();
    }

    public CannotProcessCaseException(Throwable cause) {
        super(cause);
    }
}
