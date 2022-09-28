package ru.stepagin.server.responseInterpreter.commands;


import ru.stepagin.common.dataTransfer.Request;
import ru.stepagin.common.dataTransfer.Response;
import ru.stepagin.common.lab.Worker;
import ru.stepagin.server.responseInterpreter.Interpreter;
import ru.stepagin.server.workersManager.exceptions.ExistenceException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class Insert extends Command {
    public Insert(Interpreter interpreter) {
        super(interpreter);
    }

    public Response execute(Request rq) {
        ArrayList<String> messages = new ArrayList<>();
        String key = (String) rq.attachments.get("key").get();
        Worker w = (Worker) rq.attachments.get("worker").get();
        try {
            manager.insert(key, w);
            messages.add("Элемент успешно добавлен");
        } catch (ExistenceException ex) {
            messages.add("Элемент уже существует");
        }


        return new Response(Response.Status.OK, messages, new HashMap<>());
    }

}
