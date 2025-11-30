package ru.daniil4jk.strongram.longpolling;

import org.apache.commons.lang3.tuple.Pair;
import ru.daniil4jk.strongram.core.bot.BotCredentials;
import ru.daniil4jk.strongram.core.bot.ChainedBot;
import ru.daniil4jk.strongram.core.chain.Chain;
import ru.daniil4jk.strongram.core.chain.handler.preinstalled.AddDefaultKeyboardHandler;
import ru.daniil4jk.strongram.core.chain.handler.preinstalled.CannotMatchCommandHandler;
import ru.daniil4jk.strongram.core.chain.handler.preinstalled.ExceptionReportHandler;
import ru.daniil4jk.strongram.core.chain.handler.preinstalled.MultiCommandHandler;

public class Main extends ChainedBot {
    public static void main(String[] args) throws Exception {
        var app = new LongPollingBotApplication();
        app.registerBot(new MultithreadingLongPollingBotWrapper(new Main()));
    }


    public Main() {
        super(new BotCredentials(
                "8511592818:AAF4280EOybHpQJA0jEFStBQtXAWMB55dSY",
                "qwerty_top_test_bot"
        ));
    }

    @Override
    protected void configureChain(Chain chain) {
        chain
                .add(new ExceptionReportHandler(ExceptionReportHandler.Formatters.debug()))
                .add(new AddDefaultKeyboardHandler())
                .add(new MultiCommandHandler(
                        Pair.of("/greet", (ctx, strings) -> ctx.respond("Привет " + String.join(" ", strings)))
                ))
                .add(new CannotMatchCommandHandler());
    }

    private void throwRecursive(int frames) {
        if (frames == 0) {
            throw new RuntimeException("рекурсивная ошибка");
        }
        throwRecursive(frames - 1);
    }
}
