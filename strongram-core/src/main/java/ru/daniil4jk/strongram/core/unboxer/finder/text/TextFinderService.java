package ru.daniil4jk.strongram.core.unboxer.finder.text;

import lombok.Getter;
import ru.daniil4jk.strongram.core.unboxer.finder.FinderService;

public class TextFinderService extends FinderService<String> {
    @Getter
    private static final TextFinderService instance = new TextFinderService();

    private TextFinderService() {
    }

    @Override
    protected Class<String> getOutputClass() {
        return String.class;
    }
}
