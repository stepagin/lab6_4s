package ru.stepagin.client.interpreter.commands;


import ru.stepagin.client.interpreter.Interpreter;
import ru.stepagin.common.dataTransfer.Request;
import ru.stepagin.common.dataTransfer.Response;
import ru.stepagin.common.dataTransfer.Utils;

import java.util.HashMap;

public class PrintFieldAscendingPosition extends InterpreterCommand {
    public PrintFieldAscendingPosition(Interpreter interpreter) {
        super(interpreter);
    }

    public void execute() {
        Request rq = new Request("print_field_ascending_position", new HashMap<>());
        if (client.sendRequest(rq)) {
            Response resp = client.receive();
            Utils.printResponseMessages(resp);
        }

    }

    @Override
    public String info() {
        return "вывести список рабочих, отсортированный по должности в формате key - position";
    }
}
