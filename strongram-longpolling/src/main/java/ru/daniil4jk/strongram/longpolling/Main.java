package ru.daniil4jk.strongram.longpolling;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.daniil4jk.strongram.core.bot.BotCredentials;
import ru.daniil4jk.strongram.core.bot.ChainedBot;
import ru.daniil4jk.strongram.core.chain.Chain;
import ru.daniil4jk.strongram.core.chain.context.Context;
import ru.daniil4jk.strongram.core.chain.handler.BaseHandler;
import ru.daniil4jk.strongram.core.chain.handler.preinstalled.AddDefaultKeyboardHandler;

import java.util.List;

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
        chain
                .add(new AddDefaultKeyboardHandler())
                .add(new BaseHandler() {
                    @Override
                    protected void process(Context ctx) {
                        var keyboard = new ReplyKeyboardMarkup(
                                List.of(
                                        new KeyboardRow(List.of(
                                                new KeyboardButton("имя кнопки")
                                        ))
                                )
                        );
                        ctx.getState().setByName(AddDefaultKeyboardHandler.DEFAULT_KEYBOARD_CONTEXT_FIELD_NAME, keyboard);
                        ctx.respond("ответ");
                    }
                });
    }
}
