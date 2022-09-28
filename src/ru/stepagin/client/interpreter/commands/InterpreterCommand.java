package ru.stepagin.client.interpreter.commands;

import ru.stepagin.client.Client;
import ru.stepagin.client.interpreter.Interpreter;
import ru.stepagin.common.io.InputManager;
import ru.stepagin.common.io.OutputManager;

public abstract class InterpreterCommand extends Command {
    Interpreter interpreter;
    InputManager inputManager;
    OutputManager outputManager;
    Client client;

    public InterpreterCommand(Interpreter interpreter) {

        this.interpreter = interpreter;
        this.inputManager = interpreter.getInputManager();
        this.outputManager = interpreter.getOutputManager();
        this.client = interpreter.getClient();
    }

    public String info() {
        return "";
    }

    ;
}
