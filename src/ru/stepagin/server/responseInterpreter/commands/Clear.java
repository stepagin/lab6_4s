package ru.stepagin.server.responseInterpreter.commands;


import ru.stepagin.common.dataTransfer.Request;
import ru.stepagin.common.dataTransfer.Response;
import ru.stepagin.server.responseInterpreter.Interpreter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Clear extends Command {
    public Clear(Interpreter interpreter) {
        super(interpreter);
    }

    public Response execute(Request rq) {
        ArrayList<String> messages = new ArrayList<>();
        manager.clear();

        messages.add("Коллекция очищена");
        return new Response(Response.Status.OK, messages, new HashMap<>());


    }
}
