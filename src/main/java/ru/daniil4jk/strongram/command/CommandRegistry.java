package ru.daniil4jk.strongram.command;


import java.util.Collection;
import java.util.Map;

/**
 * This Interface represents the gateway for registering and deregistering commands.
 *
 * @author Timo Schulz (Mit0x2)
 */
public interface CommandRegistry {

    /**
     * register a command
     *
     * @param botCommand the command to register
     * @return whether the command could be registered, was not already registered
     */
    boolean register(BotCommand botCommand);

    /**
     * register multiple commands
     *
     * @param botCommands commands to register
     * @return map with results of the command register per command
     */
    Map<BotCommand, Boolean> registerAll(BotCommand... botCommands);

    /**
     * deregister a command
     *
     * @param botCommand the command to deregister
     * @return whether the command could be deregistered, was registered
     */
    boolean deregister(BotCommand botCommand);

    /**
     * deregister multiple commands
     *
     * @param botCommands commands to deregister
     * @return map with results of the command deregistered per command
     */
    Map<BotCommand, Boolean> deregisterAll(BotCommand... botCommands);

    /**
     * get a collection of all registered commands
     *
     * @return a collection of registered commands
     */
    Collection<BotCommand> getAll();

    /**
     * get registered command
     *
     * @return registered command if exists or null if not
     */
    BotCommand get(String commandIdentifier);

    boolean contains(String commandIdentifier);
}