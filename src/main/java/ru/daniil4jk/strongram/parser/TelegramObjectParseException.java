package ru.daniil4jk.strongram.parser;

public class TelegramObjectParseException extends UnsupportedOperationException {
    public TelegramObjectParseException(String message) {
        super(message);
    }

    public TelegramObjectParseException(String message, Exception cause) {
        super(message, cause);
    }

    public TelegramObjectParseException(Exception exception) {
        super(exception);
    }
}
