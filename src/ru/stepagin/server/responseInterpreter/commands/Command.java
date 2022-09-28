package ru.stepagin.server.responseInterpreter.commands;

import ru.stepagin.common.dataTransfer.Request;
import ru.stepagin.common.dataTransfer.Response;
import ru.stepagin.server.responseInterpreter.Interpreter;
import ru.stepagin.server.workersManager.WorkersManager;

public abstract class Command {
    Interpreter interpreter;
    WorkersManager manager;
    public Command(Interpreter interpreter) {
        this.interpreter = interpreter;
        this.manager = this.interpreter.getManager();
    }

    public abstract Response execute(Request rq);

}
