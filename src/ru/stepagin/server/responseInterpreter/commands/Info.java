package ru.stepagin.server.responseInterpreter.commands;


import ru.stepagin.common.dataTransfer.Request;
import ru.stepagin.common.dataTransfer.Response;
import ru.stepagin.server.responseInterpreter.Interpreter;

import java.util.ArrayList;
import java.util.HashMap;

public class Info extends Command {
    public Info(Interpreter interpreter) {
        super(interpreter);
    }

    public Response execute(Request rq) {

        ArrayList<String> messages = new ArrayList<>();

        messages.add("Дата создания: " + manager.creationTime);
        messages.add("Кол-во элементов: " + manager.getWorkers().keySet().size());
        messages.add("Тип: " + manager.getClass());
        return new Response(Response.Status.OK, messages, new HashMap<>());


    }

}
