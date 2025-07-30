package ru.daniil4jk.strongram.spring.webhook;

import org.telegram.telegrambots.webhook.starter.SpringTelegramWebhookBot;
import ru.daniil4jk.strongram.core.Bot;
import ru.daniil4jk.strongram.webhook.WebhookBotWrapper;

import java.net.URL;

public class SpringWebhookBotAdapter extends SpringTelegramWebhookBot {
    public SpringWebhookBotAdapter(WebhookBotWrapper webhookBot) {
        super(webhookBot.getBotPath(),
                webhookBot::consumeUpdate,
                webhookBot::runSetWebhook,
                webhookBot::runDeleteWebhook);
    }

    public static SpringWebhookBotAdapter create(Bot bot, URL botUrl) {
        return new SpringWebhookBotAdapter(new WebhookBotWrapper(botUrl, bot));
    }
}
