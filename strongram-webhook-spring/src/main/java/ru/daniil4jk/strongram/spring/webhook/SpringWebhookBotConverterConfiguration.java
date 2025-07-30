package ru.daniil4jk.strongram.spring.webhook;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.webhook.starter.SpringTelegramWebhookBot;
import ru.daniil4jk.strongram.core.Bot;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SpringWebhookBotConverterConfiguration {
    private final PathIdentityChecker pathIdentityChecker = new PathIdentityChecker();
    @Autowired(required = false)
    private WebhookBotConfiguration webhookConfiguration;

    @Bean
    public List<SpringTelegramWebhookBot> convertWebhookBots(@NotNull Map<String, Bot> bots) {
        List<SpringTelegramWebhookBot> springBots = new ArrayList<>();
        for (var entry : bots.entrySet()) {
            springBots.add(convertBotToSpringWebhookBot(entry.getKey(), entry.getValue()));
        }
        return springBots;
    }

    private @NotNull SpringTelegramWebhookBot convertBotToSpringWebhookBot(String qualifier, Bot bot) {
        String url = toFullUrl(getBotPath(qualifier, bot));
        try {
            return SpringWebhookBotAdapter.create(bot, new URL(url));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("baseUrl: \"%s\" or botPath: \"%s\" is wrong"
                    .formatted(webhookConfiguration.getBaseUrl(), getBotPath(qualifier, bot)), e);
        }
    }

    private @NotNull String toFullUrl(String path) {
        if (webhookConfiguration == null) WebhookBotConfiguration.throwEmptyBaseUrlException();
        String baseUrl = webhookConfiguration.getBaseUrl();

        if (!baseUrl.trim().endsWith("/")) baseUrl += "/";
        if (path.startsWith("/")) path = path.replaceFirst("/", "");

        return baseUrl + path;
    }

    private String getBotPath(String qualifier, Object bot) {
        String configPath = webhookConfiguration.getPaths().get(qualifier);
        if (configPath != null) {
            pathIdentityChecker.check(configPath, bot);
            return configPath;
        }

        if (bot.getClass().isAnnotationPresent(BotPath.class)) {
            BotPath annotation = bot.getClass().getAnnotation(BotPath.class);
            String annotationPath = annotation.path();
            if (annotationPath != null) {
                pathIdentityChecker.check(annotationPath, bot);
                return annotationPath;
            }
        }

        return "";
    }


    private static class PathIdentityChecker {
        private final Map<String, Object> pathToBot = new HashMap<>();

        public void check(String path, Object bot) {
            if (pathToBot.get(path) != null) {
                throw new IllegalArgumentException(("Path %s cannot be associated with bot %s, " +
                        "because already associated with bot %s")
                        .formatted(path, bot, pathToBot.get(path)));
            }
            pathToBot.put(path, bot);
        }
    }
}
