package ru.daniil4jk.strongram.core.bot;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * Abstract base class for implementing Telegram bots.
 * Provides common functionality such as storing the bot's username and managing the Telegram client instance.
 * This class is designed to be extended by concrete bot implementations.
 *
 * <p>Thread safety: The {@link TelegramClient} field is marked as volatile to ensure visibility
 * across threads, making this class suitable for use in concurrent environments.</p>
 *
 * @see Bot
 */
@Getter
@Slf4j
@ToString
@EqualsAndHashCode
public abstract class BaseBot implements Bot {
    /**
     * The username of the bot as registered with Telegram.
     * This is immutable after instantiation.
     */
    @Getter
    private final String username;

    /**
     * The Telegram client used to communicate with the Telegram Bot API.
     * Access to this client is managed internally and can only be set once.
     *
     * @implNote This field is volatile to ensure thread-safe publication after construction.
     */
    private volatile TelegramClient client;

    /**
     * Constructs a new BaseBot with the specified username.
     *
     * @param username the bot's username; must not be null
     */
    public BaseBot(String username) {
        this.username = username;
    }

    /**
     * Constructs a new BaseBot with the specified Telegram client and username.
     *
     * @param telegramClient the Telegram client to use for API interactions; may be null initially
     * @param username       the bot's username; must not be null
     */
    public BaseBot(TelegramClient telegramClient, String username) {
        this.client = telegramClient;
        this.username = username;
    }

    /**
     * Checks whether a Telegram client has been assigned to this bot.
     *
     * @return true if the client is set; false otherwise
     */
    @Override
    public boolean hasClient() {
        return client != null;
    }

    /**
     * Sets the Telegram client for this bot.
     * The client can only be set once. Attempting to set it more than once will result in an exception.
     *
     * @param client the Telegram client to assign; must not be null
     * @throws IllegalCallerException if the client has already been set
     */
    @Override
    public void setClient(TelegramClient client) {
        if (this.client != null) {
            throw new IllegalCallerException("Client already sat");
        }
        this.client = client;
    }
}