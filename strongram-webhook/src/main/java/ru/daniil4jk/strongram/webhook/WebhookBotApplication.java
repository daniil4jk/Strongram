package ru.daniil4jk.strongram.webhook;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.webhook.TelegramBotsWebhookApplication;
import org.telegram.telegrambots.webhook.WebhookOptions;

public class WebhookBotApplication extends TelegramBotsWebhookApplication {
    public WebhookBotApplication() throws TelegramApiException {
    }

    public WebhookBotApplication(WebhookOptions webhookOptions) throws TelegramApiException {
        super(webhookOptions);
    }

    public void registerBot(@NotNull WebhookBotWrapper bot) throws TelegramApiException {
        super.registerBot(bot);
    }
}
