package ru.stepagin.client.builders.lineBuilders;

import ru.stepagin.client.builders.Builder;
import ru.stepagin.common.io.InputManager;
import ru.stepagin.common.io.OutputManager;

public abstract class LineBuilder<T extends Object> extends Builder<T> {
    InputManager inputManager;
    OutputManager outputManager;

    public LineBuilder(InputManager inputManager, OutputManager outputManager) {
        this.inputManager = inputManager;
        this.outputManager = outputManager;
    }


}
