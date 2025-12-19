package ru.daniil4jk.strongram.core.context.request;

import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.io.File;

public class RespondFactory {
    public static SendMediaBotMethod<Message> toMessage(String text, TelegramUUID uuid,
                                                        File file, RequestContext.MediaType type) {
        return switch (type) {
            case Photo -> SendPhoto.builder()
                    .photo(new InputFile(file))
                    .caption(text)
                    .chatId(uuid.getReplyChatId())
                    .build();
            case Video -> SendVideo.builder()
                    .video(new InputFile(file))
                    .caption(text)
                    .chatId(uuid.getReplyChatId())
                    .build();
            case Audio -> SendAudio.builder()
                    .audio(new InputFile(file))
                    .caption(text)
                    .chatId(uuid.getReplyChatId())
                    .build();
            case Document -> SendDocument.builder()
                    .document(new InputFile(file))
                    .caption(text)
                    .chatId(uuid.getReplyChatId())
                    .build();
        };
    }
}
