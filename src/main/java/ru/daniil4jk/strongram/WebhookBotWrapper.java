package ru.daniil4jk.strongram;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.DeleteWebhook;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.webhook.DefaultTelegramWebhookBot;
import org.telegram.telegrambots.webhook.TelegramWebhookBot;

import java.util.function.Function;

public class WebhookBotWrapper implements TelegramWebhookBot {
    private final String botPath;
    private final TelegramBot bot;

    public WebhookBotWrapper(String botPath, TelegramBot bot) {
        this.botPath = botPath;
        this.bot = bot;
    }

    @Override
    public void runDeleteWebhook() {
        bot.getClient().execute(DeleteWebhook.builder()
                .dropPendingUpdates(false)
                .build());
    }

    @Override
    public void runSetWebhook() {
        bot.getClient().execute(SetWebhook.builder()
                .url(url)
                .build());
    }

    @Override
    public BotApiMethod<?> consumeUpdate(Update update) {
        return bot.apply(update);
    }

    @Override
    public String getBotPath() {
        return botPath;
    }
}
