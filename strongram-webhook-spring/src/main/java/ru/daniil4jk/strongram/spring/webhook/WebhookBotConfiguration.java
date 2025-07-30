package ru.daniil4jk.strongram.spring.webhook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("strongram.webhook")
public class WebhookBotConfiguration {
    private String baseUrl;

    public String getBaseUrl() {
        if (baseUrl == null || baseUrl.isEmpty()) {
            throwEmptyBaseUrlException();
        }
        return baseUrl;
    }

    public static void throwEmptyBaseUrlException() {
        throw new IllegalArgumentException("You try create WebhookBot without URL in configuration. " +
                "Please setup URL of your https server to strongram.webhook.path-to-your-server " +
                "or use LongPollingBot");
    }
}
