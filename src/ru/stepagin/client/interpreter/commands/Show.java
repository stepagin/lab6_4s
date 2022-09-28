package ru.stepagin.client.interpreter.commands;


import ru.stepagin.client.interpreter.Interpreter;
import ru.stepagin.common.dataTransfer.Request;
import ru.stepagin.common.dataTransfer.Response;
import ru.stepagin.common.dataTransfer.Utils;

import java.util.HashMap;

public class Show extends InterpreterCommand {
    public Show(Interpreter interpreter) {
        super(interpreter);
    }

    public void execute() {
        Request rq = new Request("show", new HashMap<>());
        if (client.sendRequest(rq)) {
            Response resp = client.receive();
            Utils.printResponseMessages(resp);
        }


    }

    @Override
    public String info() {
        return "показывает список элементов коллекции";
    }
}
