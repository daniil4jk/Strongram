package ru.daniil4jk.strongram.core.command;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CommandRegistryImpl implements CommandRegistry {
    private final Map<String, BotCommand> commandRegistryMap = new HashMap<>();

    public CommandRegistryImpl() {
    }

    @Override
    public final boolean register(@NotNull BotCommand botCommand) {
        if (commandRegistryMap.containsKey(botCommand.getCommandIdentifier())) {
            return false;
        }
        commandRegistryMap.put(botCommand.getCommandIdentifier(), botCommand);
        return true;
    }

    @NotNull
    @Override
    public final Map<BotCommand, Boolean> registerAll(@NotNull BotCommand... botCommands) {
        Map<BotCommand, Boolean> resultMap = new HashMap<>(botCommands.length);
        for (BotCommand botCommand : botCommands) {
            resultMap.put(botCommand, register(botCommand));
        }
        return resultMap;
    }

    @Override
    public final boolean deregister(@NotNull BotCommand botCommand) {
        if (commandRegistryMap.containsKey(botCommand.getCommandIdentifier())) {
            commandRegistryMap.remove(botCommand.getCommandIdentifier());
            return true;
        }
        return false;
    }

    @NotNull
    @Override
    public final Map<BotCommand, Boolean> deregisterAll(@NotNull BotCommand... botCommands) {
        Map<BotCommand, Boolean> resultMap = new HashMap<>(botCommands.length);
        for (BotCommand botCommand : botCommands) {
            resultMap.put(botCommand, deregister(botCommand));
        }
        return resultMap;
    }

    @Override
    public BotCommand get(String commandIdentifier) {
        return commandRegistryMap.get(commandIdentifier);
    }

    @Override
    public boolean contains(String commandIdentifier) {
        return commandRegistryMap.containsKey(commandIdentifier);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public Collection<BotCommand> getAll() {
        return commandRegistryMap.values();
    }
}
