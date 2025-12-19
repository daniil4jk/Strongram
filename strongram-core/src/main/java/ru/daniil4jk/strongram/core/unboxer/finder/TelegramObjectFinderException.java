package ru.daniil4jk.strongram.core.unboxer.finder;

public class TelegramObjectFinderException extends UnsupportedOperationException {
    public TelegramObjectFinderException(String message) {
        super(message);
    }

    public TelegramObjectFinderException(String message, Exception cause) {
        super(message, cause);
    }

    public TelegramObjectFinderException(Exception exception) {
        super(exception);
    }
}
