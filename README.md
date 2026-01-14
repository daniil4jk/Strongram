# Strongram

A modern Java framework for developing Telegram bots with support for dialogs, commands, filters, and a flexible message handling system.

## 📖 Description

Strongram is a powerful and flexible framework for creating Telegram bots in Java. Some of its features:

- **Modular architecture** - connect only the components you need (Long Polling or Webhook)
- **Handler chains** - Chain of Responsibility pattern for flexible incoming message processing
- **Command handling** - support for commands in private messages, group chats, and inline mode
- **Dialog system** - easily create multi-step dialogs with state machines
- **Filters** - declarative message filtering before they reach your logic
- **Interactive keyboards** - declare keyboard button logic directly in the buttons
- **Smart Responder** - send messages in one line, without manually creating telegram-specific DTOs

### 📋 Requirements

- Java 17 or higher
- Maven 3.6+

### 📦 Installation

Add the dependency to your `pom.xml`:

#### For Long Polling

```xml
<dependency>
    <groupId>ru.daniil4jk</groupId>
    <artifactId>strongram-longpolling</artifactId>
    <version>0.5.0</version>
</dependency>
```

#### For Webhook

```xml
<dependency>
    <groupId>ru.daniil4jk</groupId>
    <artifactId>strongram-webhook</artifactId>
    <version>0.5.0</version>
</dependency>
```

## 🚀 Usage Examples

### 1. Echo Bot — "Hello World" Application

A simple bot that repeats all text messages from users.

```java
import ru.daniil4jk.strongram.core.bot.ChainedBot;
import ru.daniil4jk.strongram.core.chain.factory.ChainFactory;
import ru.daniil4jk.strongram.core.chain.factory.configurable.ConfigurableChainFactory;

public class EchoBot extends ChainedBot {
    
    public EchoBot(String botToken) {
        super(botToken);
    }
    
    @Override
    protected ChainFactory getChain() {
        return new ConfigurableChainFactory(chain -> {
            chain.add(new EchoHandler());
        });
    }
}
```

```java
import ru.daniil4jk.strongram.core.context.request.RequestContext;
import ru.daniil4jk.strongram.core.filter.Filter;
import ru.daniil4jk.strongram.core.filter.Filters;
import ru.daniil4jk.strongram.core.handler.FilteredHandler;
import ru.daniil4jk.strongram.core.unboxer.As;

public class EchoHandler extends FilteredHandler {
    
    @Override
    protected Filter getFilter() {
        return Filters.hasMessageText();
    }
    
    @Override
    protected void processFiltered(RequestContext ctx) {
        String text = ctx.getRequest(As.messageText());
        ctx.getResponder().send("You said: " + text);
    }
}
```

### 2. Bot with Command Handler

A bot with command '/hello'

```java
import ru.daniil4jk.strongram.core.bot.ChainedBot;
import ru.daniil4jk.strongram.core.chain.factory.ChainFactory;
import ru.daniil4jk.strongram.core.chain.factory.configurable.ConfigurableChainFactory;

public class CommandBot extends ChainedBot {
    
    public CommandBot(String botToken) {
        super(botToken);
    }
    
    @Override
    protected ChainFactory getChain() {
        return new ConfigurableChainFactory(chain -> {
            chain.add(new BasicCommandHandler());
        });
    }
}
```

```java
import ru.daniil4jk.strongram.core.command.EachCommandHandler;
import ru.daniil4jk.strongram.core.context.request.RequestContext;
import ru.daniil4jk.strongram.core.handler.preinstalled.TextCommandHandler;

import java.util.Map;

public class CommandHandler extends CommandHandler {
    
    @Override
    protected Map<String, EachCommandHandler> getCommands() {
        return Map.of(
            "hello", this::handleHello
        );
    }
    
    private void handleHello(RequestContext ctx, String[] args) {
        ctx.getResponder().send("Hello");
    }
}
```

### 3. Error Handling and Unknown Commands

An example with exception handling and situations when a command is not recognized.

```java
import ru.daniil4jk.strongram.core.bot.ChainedBot;
import ru.daniil4jk.strongram.core.chain.factory.ChainFactory;
import ru.daniil4jk.strongram.core.chain.factory.configurable.ConfigurableChainFactory;
import ru.daniil4jk.strongram.core.handler.preinstalled.ExceptionReportHandler;
import ru.daniil4jk.strongram.core.report.exception.ExceptionFormatters;

public class RobustBot extends ChainedBot {
    
    public RobustBot(String botToken) {
        super(botToken);
    }
    
    @Override
    protected ChainFactory getChain() {
        return new ConfigurableChainFactory(chain -> {
            // Built-in exception handler (should be first to catch exceptions via try-catch)
            chain.add(new ExceptionReportHandler(ExceptionFormatters.debug()));
            
            // Our command handler
            chain.add(new CommandHandler());
            
            // Built-in unknown command handler (should be last)
            chain.add(new CannotProcessHandler());
        });
    }
}
```

```java
import ru.daniil4jk.strongram.core.command.EachCommandHandler;
import ru.daniil4jk.strongram.core.context.request.RequestContext;
import ru.daniil4jk.strongram.core.handler.preinstalled.TextCommandHandler;

import java.util.Map;

public class CommandHandler extends CommandHandler {
    
    @Override
    protected Map<String, EachCommandHandler> getCommands() {
        return Map.of(
            "start", this::handleStart,
            "error", this::handleError,
            "divide", this::handleDivide
        );
    }
    
    private void handleStart(RequestContext ctx, String[] args) {
        ctx.getResponder().send("Bot started! Try /error");
    }
    
    private void handleError(RequestContext ctx, String[] args) {
        throw new RuntimeException("This is a test error to demonstrate exception handling!");
    }
}
```

### 4. Sending Files

Example of sending different types of files.

```java
import ru.daniil4jk.strongram.core.bot.ChainedBot;
import ru.daniil4jk.strongram.core.chain.factory.ChainFactory;
import ru.daniil4jk.strongram.core.chain.factory.configurable.ConfigurableChainFactory;

public class FileBot extends ChainedBot {
    
    public FileBot(String botToken) {
        super(botToken);
    }
    
    @Override
    protected ChainFactory getChain() {
        return new ConfigurableChainFactory(chain -> {
            chain.add(new FileCommandHandler());
        });
    }
}
```

```java
import ru.daniil4jk.strongram.core.command.EachCommandHandler;
import ru.daniil4jk.strongram.core.context.request.RequestContext;
import ru.daniil4jk.strongram.core.handler.preinstalled.TextCommandHandler;
import ru.daniil4jk.strongram.core.response.responder.smart.SmartResponder;

import java.io.File;
import java.util.Map;

public class FileCommandHandler extends CommandHandler {
    
    @Override
    protected Map<String, EachCommandHandler> getCommands() {
        return Map.of(
            "photo", this::sendPhoto,
            "document", this::sendDocument,
            "video", this::sendVideo
        );
    }
    
    private void sendPhoto(RequestContext ctx, String[] args) {
        File file = new File("path/to/photo.jpg");
        
        ctx.getResponder().send(
            "📷 Here's your photo!",
            file,
            SmartResponder.MediaType.Photo
        );
    }
    
    private void sendDocument(RequestContext ctx, String[] args) {
        File file = new File("path/to/document.pdf");
        
        ctx.getResponder().send(
            "📄 Here's your document!",
            file,
            SmartResponder.MediaType.Document
        );
    }
    
    private void sendVideo(RequestContext ctx, String[] args) {
        File file = new File("path/to/video.mp4");
        
        ctx.getResponder().send(
            "🎥 Here's your video!",
            file,
            SmartResponder.MediaType.Video
        );
    }
}
```

### 5. Basic Dialog

A comprehensive example of a multi-step dialog using the Builder pattern.

```java
import ru.daniil4jk.strongram.core.bot.ChainedBot;
import ru.daniil4jk.strongram.core.chain.factory.ChainFactory;
import ru.daniil4jk.strongram.core.chain.factory.configurable.ConfigurableChainFactory;
import ru.daniil4jk.strongram.core.handler.preinstalled.DialogHandler;

public class PizzaBot extends ChainedBot {
    
    public PizzaBot(String botToken) {
        super(botToken);
    }
    
    @Override
    protected ChainFactory getChain() {
        return new ConfigurableChainFactory(chain -> {
            chain.add(new PizzaOrderCommandHandler());
            chain.add(new DialogHandler());
        });
    }
}
```

```java
import ru.daniil4jk.strongram.core.command.EachCommandHandler;
import ru.daniil4jk.strongram.core.context.request.RequestContext;
import ru.daniil4jk.strongram.core.dialog.DialogImpl;
import ru.daniil4jk.strongram.core.dialog.part.BuildableDialogPart;
import ru.daniil4jk.strongram.core.filter.Filters;
import ru.daniil4jk.strongram.core.handler.preinstalled.DialogHandler;
import ru.daniil4jk.strongram.core.handler.preinstalled.TextCommandHandler;
import ru.daniil4jk.strongram.core.unboxer.As;

import java.util.Map;

public class PizzaOrderCommandHandler extends TextCommandHandler {
    
    @Override
    protected Map<String, EachCommandHandler> getCommands() {
        return Map.of("order", this::startOrder);
    }
    
    private void startOrder(RequestContext ctx, String[] args) {
        DialogImpl<PizzaState> dialog = DialogImpl.<PizzaState>builder()
            .initState(PizzaState.ASK_NAME)
            .part(PizzaState.ASK_NAME, BuildableDialogPart.<PizzaState>builder()
                .filter(Filters.hasMessageText())
                .firstNotification((context, storage) -> {
                    context.getResponder().send("Enter pizza name");
                })
                .repeatNotification((context, storage) -> {
                    context.getResponder().send("You're still in the dialog, enter pizza name");
                })
                .handler((context, dCtx) -> {
                    String pizzaName = context.getRequest(As.messageText());
                    dCtx.getDialogScopeStorage().set("pizzaName", pizzaName);
                    dCtx.setState(PizzaState.ASK_ADDRESS);
                })
                .build())
            .part(PizzaState.ASK_ADDRESS, BuildableDialogPart.<PizzaState>builder()
                .filter(Filters.hasMessageText())
                .firstNotification((context, storage) -> {
                    context.getResponder().send("Now specify the delivery address");
                })
                .repeatNotification((context, storage) -> {
                    context.getResponder().send("You're still in the dialog, specify the delivery address");
                })
                .handler((context, dCtx) -> {
                    String address = context.getRequest(As.messageText());
                    String pizzaName = dCtx.getDialogScopeStorage().get("pizzaName");
                    
                    context.getResponder().send("""
                        Order accepted!
                        
                        Pizza: %s
                        Address: %s
                        """.formatted(pizzaName, address));
                    
                    dCtx.stop();
                })
                .build())
            .build();
        
        ctx.getRequestScopeStorage().add(DialogHandler.DIALOGS_CONTEXT_FIELD_NAME, dialog);
    }
    
    public enum PizzaState {
        ASK_NAME,
        ASK_ADDRESS
    }
}
```

### 6. Dialog with Complex Logic (ExtendableDialogPart)

Extending the previous example with a payment system.
First, let's copy the `PizzaOrderCommandHandler` class, adding one step: PaymentDialogPart

```java
import ru.daniil4jk.strongram.core.command.EachCommandHandler;
import ru.daniil4jk.strongram.core.context.request.RequestContext;
import ru.daniil4jk.strongram.core.dialog.DialogImpl;
import ru.daniil4jk.strongram.core.dialog.part.BuildableDialogPart;
import ru.daniil4jk.strongram.core.filter.Filters;
import ru.daniil4jk.strongram.core.handler.preinstalled.DialogHandler;
import ru.daniil4jk.strongram.core.handler.preinstalled.TextCommandHandler;
import ru.daniil4jk.strongram.core.unboxer.As;

import java.util.Map;

public class PizzaOrderCommandHandler extends TextCommandHandler {
    
    @Override
    protected Map<String, EachCommandHandler> getCommands() {
        return Map.of("order", this::startOrder);
    }
    
    private void startOrder(RequestContext ctx, String[] args) {
        DialogImpl<PizzaState> dialog = DialogImpl.<PizzaState>builder()
            .initState(PizzaState.ASK_NAME)
            .part(PizzaState.ASK_NAME, BuildableDialogPart.<PizzaState>builder()
                .filter(Filters.hasMessageText())
                .firstNotification((context, storage) -> {
                    context.getResponder().send("Enter pizza name");
                })
                .repeatNotification((context, storage) -> {
                    context.getResponder().send("You're still in the dialog, enter pizza name");
                })
                .handler((context, dCtx) -> {
                    String pizzaName = context.getRequest(As.messageText());
                    dCtx.getDialogScopeStorage().set("pizzaName", pizzaName);
                    dCtx.setState(PizzaState.ASK_ADDRESS);
                })
                .build())
            .part(PizzaState.ASK_ADDRESS, BuildableDialogPart.<PizzaState>builder()
                .filter(Filters.hasMessageText())
                .firstNotification((context, storage) -> {
                    context.getResponder().send("Now specify the delivery address");
                })
                .repeatNotification((context, storage) -> {
                    context.getResponder().send("You're still in the dialog, specify the delivery address");
                })
                .handler((context, dCtx) -> {
                    String address = context.getRequest(As.messageText());
                    dCtx.getDialogScopeStorage().set("address", address);
                    dCtx.setState(PizzaState.PAYMENT);
                })
                .build())
            .part(PizzaState.PAYMENT, new PaymentDialogPart())
            .build();
        
        ctx.getRequestScopeStorage().add(DialogHandler.DIALOGS_CONTEXT_FIELD_NAME, dialog);
    }
    
    public enum PizzaState {
        ASK_NAME,
        ASK_ADDRESS,
        PAYMENT
    }
}
```

New payment logic:
```java
import ru.daniil4jk.strongram.core.context.dialog.DialogContext;
import ru.daniil4jk.strongram.core.context.request.RequestContext;
import ru.daniil4jk.strongram.core.context.storage.Storage;
import ru.daniil4jk.strongram.core.dialog.part.ExtendableDialogPart;
import ru.daniil4jk.strongram.core.filter.Filter;
import ru.daniil4jk.strongram.core.filter.Filters;
import ru.daniil4jk.strongram.core.unboxer.As;
import ru.daniil4jk.strongram.core.handler.preinstalled.PizzaOrderWithPaymentCommandHandler.PizzaState;

public class PaymentDialogPart extends ExtendableDialogPart<PizzaState> {
    
    private final PaymentService paymentService;
    
    public PaymentDialogPart() {
        this.paymentService = new PaymentService();
    }
    
    @Override
    protected Filter getFilter() {
        return Filters.hasMessageText();
    }
    
    @Override
    protected void firstNotification(RequestContext ctx, Storage storage) {
        String pizzaName = storage.get("pizzaName");
        String address = storage.get("address");
        
        ctx.getResponder().send("""
            Order Confirmation
            Pizza: %s
            Address: %s
            Cost: 500 rub.
            Send "pay" to confirm the order:
            """.formatted(pizzaName, address));
    }
    
    @Override
    protected void repeatNotification(RequestContext ctx, Storage storage) {
        firstNotification(ctx, storage);
    }
    
    @Override
    protected void accept(RequestContext ctx, DialogContext<PizzaState> dCtx) {
        String message = ctx.getRequest(As.messageText()).toLowerCase();
        
        if (message.contains("cancel")) {
            ctx.getResponder().send("Order cancelled. Use /order for a new order.");
            dCtx.stop();
            return;
        }
        
        if (!message.contains("pay")) {
            ctx.getResponder().send("Send 'pay' to confirm or 'cancel' to cancel.");
            return;
        }
        
        String pizzaName = dCtx.getDialogScopeStorage().get("pizzaName");
        String address = dCtx.getDialogScopeStorage().get("address");
        Long userId = ctx.getUUID().getUserId();
        
        try {
            // Process payment through service
            paymentService.processPayment(userId, 500);
            
            ctx.getResponder().send("""
                Payment successful!
                Pizza: %s
                Address: %s
                Expect delivery
                """.formatted(pizzaName, address)
            );
            
            dCtx.stop();
        } catch (PaymentException e) {
            ctx.getResponder().send("Payment error %s! Try again or write \"cancel\" to cancel the order.".formatted(e.getMessage()));
        }
    }
}
```

```java
public interface PaymentService {
    
    /**
     * Simulating payment processing
     * In a real application, this will be an integration with a payment system
     */
    void processPayment(Long userId, int amount) throws PaymentException;
}
```

### 7. Registering Bot as Long Polling

Running a bot using Long Polling to receive updates.

```java
import ru.daniil4jk.strongram.longpolling.LongPollingBotApplication;
import ru.daniil4jk.strongram.longpolling.adapter.LongPollingBotAdapter;

public class Main {
    
    public static void main(String[] args) {
        try {
            LongPollingBotApplication application = new LongPollingBotApplication();
            
            application.registerBot(new LongPollingBotAdapter(
                "YOUR_BOT_TOKEN",
                new Bot("YOUR_BOT_NAME")
            ));
        } catch (Exception e) {
            System.err.println("❌ Failed to start bot: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

Example with multiple bots:

```java
import ru.daniil4jk.strongram.longpolling.LongPollingBotApplication;
import ru.daniil4jk.strongram.longpolling.adapter.LongPollingBotAdapter;

public class Main {
    
    public static void main(String[] args) {
        try {
            LongPollingBotApplication application = new LongPollingBotApplication();
            
            application.registerBot(new LongPollingBotAdapter(
                "YOUR_FIRST_BOT_TOKEN",
                new Bot("YOUR_FIRST_BOT_NAME")
            ));
            
            application.registerBot(new LongPollingBotAdapter(
                "YOUR_SECOND_BOT_TOKEN",
                new Bot("YOUR_SECOND_BOT_NAME")
            ));
            
            application.registerBot(new LongPollingBotAdapter(
                "YOUR_THIRD_BOT_TOKEN",
                new Bot("YOUR_THIRD_BOT_NAME")
            ));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### 8. Registering Bot as Webhook

Running a bot using Webhook to receive updates.

```java
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.daniil4jk.strongram.webhook.WebhookBotApplication;
import ru.daniil4jk.strongram.webhook.adapter.WebhookBotAdapter;

public class Main {
    
    public static void main(String[] args) {
        try {
            WebhookBotApplication application = new WebhookBotApplication();
            
            application.registerBot(
                new WebhookBotAdapter(
                    new URI("https://yourdomain.com/webhook/bot").toURL(),
                    "YOUR_BOT_TOKEN",
                    new Bot("YOUR_BOT_NAME")
                )
            );
        } catch (Exception e) {
            System.err.println("Failed to start bot: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

## 🤝 Contributing to the Project

We welcome your participation in developing Strongram! Here's how you can help:

### How to Contribute

1. **Fork the project** - Create your own copy of the repository
2. **Create a branch** - `git checkout -b feature/AmazingFeature`
3. **Make changes** - Implement your functionality
4. **Commit** - `git commit -m 'Add some AmazingFeature'`
5. **Push to branch** - `git push origin feature/AmazingFeature`
6. **Open a Pull Request** - Describe your changes

### Contribution Rules

- Follow the existing code style
- Add tests for new functionality
- Update documentation when necessary
- Write clear commit messages
- One Pull Request = one feature/fix

### What Can Be Improved

- 📝 Documentation and examples
- 🐛 Bug fixes
- ✨ New features
- 🎨 API improvements
- 🔧 Performance optimization
- 🧪 Writing tests

### Bug Reports

When creating an issue, specify:
- Strongram version
- Java version
- Problem description
- Steps to reproduce
- Expected behavior
- Actual behavior

## 📄 License

The project is distributed under the **MIT License**.

## 🙏 Acknowledgments

Special thanks to the following projects:

- **[TelegramBots Java Library](https://github.com/rubenlagus/TelegramBots)** - excellent base library for working with Telegram Bot API by [@rubenlagus](https://github.com/rubenlagus)
- **[Project Lombok](https://projectlombok.org/)** - reducing boilerplate code
- **[Apache Log4j](https://logging.apache.org/log4j/)** - logging system
- All [contributors](https://github.com/daniil4jk/strongram/graphs/contributors) to the project

Thank you to everyone who uses Strongram and shares feedback!

---

<div align="center">

⭐ **If you like Strongram, give it a star on GitHub!** ⭐

Made with ❤️ by [daniil4jk](https://github.com/daniil4jk)

</div>
