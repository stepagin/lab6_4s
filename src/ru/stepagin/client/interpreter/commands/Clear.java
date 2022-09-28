package ru.stepagin.client.interpreter.commands;


import ru.stepagin.client.interpreter.Interpreter;
import ru.stepagin.common.dataTransfer.Request;
import ru.stepagin.common.dataTransfer.Response;
import ru.stepagin.common.dataTransfer.Utils;

import java.util.HashMap;

public class Clear extends InterpreterCommand {
    public Clear(Interpreter interpreter) {
        super(interpreter);
    }

    public void execute() {
        Request rq = new Request("clear", new HashMap<>());
        if (client.sendRequest(rq)) {
            Response resp = client.receive();
            Utils.printResponseMessages(resp);
        }

    }
}
