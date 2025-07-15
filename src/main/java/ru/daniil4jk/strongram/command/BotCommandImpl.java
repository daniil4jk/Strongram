package ru.daniil4jk.strongram.command;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public abstract class BotCommandImpl implements BotCommand {
    public static final String COMMAND_INIT_CHARACTER = "/";
    public static final String COMMAND_PARAMETER_SEPARATOR_REGEXP = "\\s+";
    private static final int COMMAND_MAX_LENGTH = 32;

    @NotNull
    @Contract(" -> new")
    public static BotCommandDefaultImpl.BotCommandDefaultImplBuilder builder() {
        return new BotCommandDefaultImpl.BotCommandDefaultImplBuilder();
    }

    private final String commandIdentifier;
    private final String description;

    protected BotCommandImpl(String commandIdentifier, String description) {
        if (commandIdentifier == null || commandIdentifier.isEmpty()) {
            throw new IllegalArgumentException("commandIdentifier for command cannot be null or empty");
        }

        if (commandIdentifier.startsWith(COMMAND_INIT_CHARACTER)) {
            commandIdentifier = commandIdentifier.substring(1);
        }

        if (commandIdentifier.length() + 1 > COMMAND_MAX_LENGTH) {
            throw new IllegalArgumentException("commandIdentifier cannot be longer than " + COMMAND_MAX_LENGTH + " (including " + COMMAND_INIT_CHARACTER + ")");
        }

        this.commandIdentifier = commandIdentifier.toLowerCase();
        this.description = description;
    }

    public final String getCommandIdentifier() {
        return commandIdentifier;
    }

    public final String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "<b>" + COMMAND_INIT_CHARACTER + getCommandIdentifier() +
                "</b>\n" + getDescription();
    }
}
