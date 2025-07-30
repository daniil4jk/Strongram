package ru.daniil4jk.strongram.spring.longpolling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import ru.daniil4jk.strongram.core.Bot;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Slf4j
public class SpringLongPollingBotConverterConfiguration {
    private static final Class<? extends Annotation> longPollingBotAnnotation = LongPollingBot.class;
    private final ApplicationContext context;

    public SpringLongPollingBotConverterConfiguration(ApplicationContext context) {
        this.context = context;
    }

    @Bean
    public List<SpringLongPollingBot> convertLongPollingBots() {
        List<SpringLongPollingBot> springBots = new ArrayList<>();
        addBotsToList(longPollingBotAnnotation, this::convertBotToSpringLongPollingBot, springBots);
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

    private SpringLongPollingBot convertBotToSpringLongPollingBot(Bot bot) {
        return new SpringLongPollingBotAdapter(bot);
    }
}
