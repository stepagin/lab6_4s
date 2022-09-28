package ru.stepagin.server.responseInterpreter.commands;


import ru.stepagin.common.dataTransfer.Request;
import ru.stepagin.common.dataTransfer.Response;
import ru.stepagin.server.responseInterpreter.Interpreter;
import ru.stepagin.server.workersManager.exceptions.IllegalDataAccessException;
import ru.stepagin.server.workersManager.exceptions.NotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class ReplaceIfLower extends Command {
    public ReplaceIfLower(Interpreter interpreter) {
        super(interpreter);
    }

    public Response execute(Request rq) {
        ArrayList<String> messages = new ArrayList<>();
        String key = (String) rq.attachments.get("key").get();
        Float salary = (Float) rq.attachments.get("salary").get();
        try {
            if (manager.replaceIfLower(key, salary)) {
                messages.add("Элемент обновлён");
            } else {
                messages.add("Элемент не обновлён");
            }
        } catch (NotFoundException ex) {
            messages.add("Элемент не найден");
        }
        catch(IllegalDataAccessException ex){
            messages.add("У вас нет доступа к данному элементу");
        }

        return new Response(Response.Status.OK, messages, new HashMap<>());


    }
}