package ru.stepagin.server.responseInterpreter.commands;


import ru.stepagin.common.dataTransfer.Request;
import ru.stepagin.common.dataTransfer.Response;
import ru.stepagin.common.lab.Position;
import ru.stepagin.server.responseInterpreter.Interpreter;

import java.util.ArrayList;
import java.util.HashMap;

public class RemoveAllByPosition extends Command {

    public RemoveAllByPosition(Interpreter interpreter) {
        super(interpreter);
    }

    public Response execute(Request rq) {
        Position position = (Position) rq.attachments.get("position").get();
        manager.removeAllByPosition(position);
        ArrayList<String> messages = new ArrayList<>();
        messages.add("Выполнено");
        return new Response(Response.Status.OK, messages, new HashMap<>());
    }

}
