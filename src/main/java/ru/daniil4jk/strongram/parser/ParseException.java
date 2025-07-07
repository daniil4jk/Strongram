package ru.daniil4jk.strongram.parser;

public class ParseException extends RuntimeException {
    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Exception cause) {
        super(message, cause);
    }

    public ParseException(Exception exception) {
        super(exception);
    }
}
