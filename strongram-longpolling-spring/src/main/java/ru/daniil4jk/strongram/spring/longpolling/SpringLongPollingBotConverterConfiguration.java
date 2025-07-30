package ru.daniil4jk.strongram.spring.longpolling;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import ru.daniil4jk.strongram.core.Bot;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

@Slf4j
public class SpringLongPollingBotConverterConfiguration {
    @Lazy
    @Autowired
    private ExecutorService executor;

    @Bean
    @Lazy
    @ConditionalOnMissingBean
    public ExecutorService defaultExecutor() {
        return Executors.newCachedThreadPool();
    }

    @Bean
    public List<SpringLongPollingBot> convertLongPollingBots(@NotNull List<Bot> bots) {
        List<SpringLongPollingBot> springBots = new ArrayList<>();
        for (Bot bot : bots) {
            springBots.add(convertBotToSpringLongPollingBot(bot));
        }
        return springBots;
    }

    private @NotNull SpringLongPollingBot convertBotToSpringLongPollingBot(@NotNull Bot bot) {
        boolean multithreadingRequired = bot.getClass().isAnnotationPresent(MultithreadingBot.class);
        if (multithreadingRequired) {
            return SpringLongPollingBotAdapter.create(bot, executor);
        } else {
            return SpringLongPollingBotAdapter.create(bot);
        }
    }
}
