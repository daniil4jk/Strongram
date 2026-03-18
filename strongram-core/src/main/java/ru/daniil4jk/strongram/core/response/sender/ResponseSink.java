package ru.daniil4jk.strongram.core.response.sender;

import ru.daniil4jk.strongram.core.response.dto.Response;

import java.util.List;
import java.util.function.Consumer;

public interface ResponseSink extends Consumer<List<Response<?>>> {
}
