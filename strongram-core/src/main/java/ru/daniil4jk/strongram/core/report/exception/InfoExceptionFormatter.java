package ru.daniil4jk.strongram.core.report.exception;

import org.jetbrains.annotations.NotNull;

class InfoExceptionFormatter implements ExceptionFormatter {
    @Override
    public String apply(Throwable th) {
        return assemblyUserMessage(th);
    }

    private @NotNull String assemblyUserMessage(Throwable th) {
        return "Произошла ошибка: " +
                (th.getLocalizedMessage() == null || th.getLocalizedMessage().isEmpty() ?
                        "неизвестная ошибка" : th.getLocalizedMessage()) +
                ". Вы можете ПОПРОБОВАТЬ снова или ОБРАТИТЬСЯ к администратору бота";
    }
}
