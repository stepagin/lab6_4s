package ru.stepagin.server.responseInterpreter.commands;

import ru.stepagin.common.dataTransfer.Request;
import ru.stepagin.common.dataTransfer.Response;
import ru.stepagin.server.responseInterpreter.Interpreter;

import java.util.ArrayList;
import java.util.HashMap;


public class RemoveGreater extends Command {
    public RemoveGreater(Interpreter interpreter) {
        super(interpreter);
    }

    public Response execute(Request rq) {
        Float salary = (Float) rq.attachments.get("salary").get();
        ArrayList<String> messages = new ArrayList<>();
        manager.removeGreater(salary);
        messages.add("Выполнено");

        return new Response(Response.Status.OK, messages, new HashMap<>());


    }

}

