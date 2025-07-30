package ru.daniil4jk.strongram.spring.webhook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Data
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("strongram.webhook")
public class WebhookBotConfiguration {
    private String baseUrl;
    private Map<String, String> paths = new HashMap<>();

    public String getBaseUrl() {
        if (baseUrl == null || baseUrl.isEmpty()) {
            throwEmptyBaseUrlException();
        }
        return baseUrl;
    }

    @Contract(value = " -> fail", pure = true)
    public static void throwEmptyBaseUrlException() {
        throw new IllegalArgumentException("You try create WebhookBot without URL in configuration. " +
                "Please setup URL of your https server to strongram.webhook.base-url " +
                "or use LongPollingBot");
    }
}
