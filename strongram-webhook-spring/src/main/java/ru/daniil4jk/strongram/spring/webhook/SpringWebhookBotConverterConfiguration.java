package ru.daniil4jk.strongram.spring.webhook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.webhook.starter.SpringTelegramWebhookBot;
import ru.daniil4jk.strongram.core.Bot;

import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Slf4j
public class SpringWebhookBotConverterConfiguration {
    private static final Class<? extends Annotation> webhookBotAnnotation = WebhookBot.class;
    private final ApplicationContext context;
    @Autowired(required = false)
    private WebhookBotConfiguration webhookConfiguration;

    public SpringWebhookBotConverterConfiguration(ApplicationContext context) {
        this.context = context;
    }

    @Bean
    public List<SpringTelegramWebhookBot> convertWebhookBots() {
        List<SpringTelegramWebhookBot> springBots = new ArrayList<>();
        addBotsToList(webhookBotAnnotation, this::convertBotToSpringWebhookBot, springBots);
        return springBots;
    }

    private <T> void addBotsToList(Class<? extends Annotation> annotation, Function<Bot, T> convert, List<T> list) {
        for (var entry : context.getBeansWithAnnotation(annotation).entrySet()) {
            if (entry.getValue() instanceof Bot bot) {
                list.add(convert.apply(bot));
            } else {
                log.warn("Object with context name \"{}\" annotated as {}. Remove this annotation from object",
                        entry.getKey(), annotation.getName());
            }
        }
    }

    private SpringTelegramWebhookBot convertBotToSpringWebhookBot(Bot bot) {
        String botUrl = getUrlForBot(bot);
        try {
            return SpringWebhookBotAdapter.create(bot, new URL(botUrl));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("baseUrl: \"%s\" or botPath: \"%s\" is wrong"
                    .formatted(webhookConfiguration.getBaseUrl(), getBotPath(bot)), e);
        }
    }

    private String getUrlForBot(Bot bot) {
        if (webhookConfiguration == null) WebhookBotConfiguration.throwEmptyBaseUrlException();

        String baseUrl = webhookConfiguration.getBaseUrl();
        String path = getBotPath(bot);

        if (!baseUrl.trim().endsWith("/")) baseUrl += "/";
        if (path.startsWith("/")) path = path.replaceFirst("/", "");

        return baseUrl + path;
    }

    private String getBotPath(Bot bot) {
        WebhookBot webhookBotAnnotation = (WebhookBot) bot.getClass()
                .getAnnotation(SpringWebhookBotConverterConfiguration.webhookBotAnnotation);
        return webhookBotAnnotation.path();
    }
}
