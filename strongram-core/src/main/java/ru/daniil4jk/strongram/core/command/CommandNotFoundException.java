package ru.daniil4jk.strongram.core.command;

public class CommandNotFoundException extends RuntimeException {
    public CommandNotFoundException(String command) {
        super("Команда %s не найдена".formatted(command));
    }
}
