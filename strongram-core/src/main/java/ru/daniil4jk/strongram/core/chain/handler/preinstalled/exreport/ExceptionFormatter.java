package ru.daniil4jk.strongram.core.chain.handler.preinstalled.exreport;

import java.util.function.Function;

public interface ExceptionFormatter extends Function<Throwable, String> {}
