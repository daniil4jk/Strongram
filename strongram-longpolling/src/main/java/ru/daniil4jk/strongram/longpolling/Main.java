package ru.daniil4jk.strongram.longpolling;

import ru.daniil4jk.strongram.core.bot.BotCredentials;
import ru.daniil4jk.strongram.core.bot.ChainedBot;
import ru.daniil4jk.strongram.core.chain.Chain;
import ru.daniil4jk.strongram.core.chain.context.Context;
import ru.daniil4jk.strongram.core.chain.filter.Filter;
import ru.daniil4jk.strongram.core.chain.handler.BaseHandler;

import static ru.daniil4jk.strongram.core.chain.filter.Filters.hasPhoto;

public class Main extends ChainedBot {
    public static void main(String[] args) throws Exception {
        var app = new LongPollingBotApplication();
        app.registerBot( new MultithreadingLongPollingBotWrapper(new Main()));

    }


    public Main() {
        super(new BotCredentials(
                "7456182814:AAGTdSd1gwJWEG8ZyvCniqHEUNWBkKlJZm4",
                ""
        ));
    }

    @Override
    protected void configureChain(Chain chain) {
        chain.add(new BaseHandler() {
            @Override
            protected Filter getFilter() {
                return hasPhoto();
            }

            @Override
            protected void process(Context ctx) {
                ctx.respond("Ого, фотка");
            }
        }).add(new BaseHandler() {
            @Override
            protected Filter getFilter() {
                return hasText().and(textContainsIgnoreCase("Привет"));
            }

            @Override
            protected void process(Context ctx) {
                ctx.respond("И тебе привет");
            }
        });
    }
}
