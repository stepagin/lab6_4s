package ru.stepagin.server.responseInterpreter.commands;


import ru.stepagin.common.dataTransfer.Request;
import ru.stepagin.common.dataTransfer.Response;
import ru.stepagin.common.lab.Worker;
import ru.stepagin.server.responseInterpreter.Interpreter;
import ru.stepagin.server.workersManager.exceptions.IllegalDataAccessException;
import ru.stepagin.server.workersManager.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;


public class Update extends Command {
    public Update(Interpreter interpreter) {
        super(interpreter);
    }

    public Response execute(Request rq) {
        ArrayList<String> messages = new ArrayList<>();
        Long id = (Long) rq.attachments.get("id").get();
        Worker w = (Worker) rq.attachments.get("worker").get();
        try {
            String key = manager.update(id, w);
            messages.add("Элемент [" + key + "] успешно обновлён");
        } catch (NotFoundException ex) {
            messages.add("Элемент не найден");
        }
        catch(IllegalDataAccessException ex){
            messages.add("У вас нет доступа к данному элементу");
        }


        return new Response(Response.Status.OK, messages, new HashMap<>());
    }
}