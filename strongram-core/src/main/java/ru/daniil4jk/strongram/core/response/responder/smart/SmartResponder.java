package ru.daniil4jk.strongram.core.response.responder.smart;

import org.telegram.telegrambots.meta.api.objects.message.Message;
import ru.daniil4jk.strongram.core.response.responder.Responder;
import ru.daniil4jk.strongram.core.response.responder.accumulating.Accumulator;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface SmartResponder extends Responder, Accumulator {
    void send(String text);

    void send(String text, File file, MediaType type);

    List<CompletableFuture<Message>> sendForObject(String text);

    List<CompletableFuture<Message>> sendForObject(String text, File file, MediaType type);

    enum MediaType {
        Photo,
        Video,
        Audio,
        Voice,
        Document
    }
}
