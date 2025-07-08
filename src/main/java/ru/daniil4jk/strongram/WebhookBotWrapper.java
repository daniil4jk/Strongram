package ru.daniil4jk.strongram;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.TelegramUrl;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.DeleteWebhook;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.webhook.TelegramWebhookBot;

@Slf4j
@RequiredArgsConstructor
public class WebhookBotWrapper implements TelegramWebhookBot {
    private final String baseUrl;
    @Getter
    private final String botPath;
    private final TelegramBot bot;

    @Override
    public void runDeleteWebhook() {
        try {
            bot.getClient().execute(DeleteWebhook.builder()
                    .dropPendingUpdates(true)
                    .build());
        } catch (TelegramApiException e) {
            log.warn("Can`t deleteWebhook to bot on the path {}", this.botPath, e);
        }
    }

    @Override
    public void runSetWebhook() {
        String botPath = this.botPath;
        if (botPath.trim().startsWith("/")) botPath = botPath.replaceFirst("/", "");

        try {
            bot.getClient().execute(SetWebhook.builder()
                    .url(baseUrl + "/" + botPath)
                    .build());
        } catch (TelegramApiException e) {
            log.error("Can`t setWebhook to bot on the path {}", this.botPath, e);
        }
    }

    @Override
    public BotApiMethod<?> consumeUpdate(Update update) {
        return bot.apply(update);
    }
}
