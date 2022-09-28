package ru.stepagin.server.responseInterpreter.commands;

import ru.stepagin.common.dataTransfer.Request;
import ru.stepagin.common.dataTransfer.Response;
import ru.stepagin.server.responseInterpreter.Interpreter;
import ru.stepagin.server.workersManager.exceptions.IllegalDataAccessException;
import ru.stepagin.server.workersManager.exceptions.NotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class RemoveKey extends Command {
    public RemoveKey(Interpreter interpreter) {
        super(interpreter);
    }

    public Response execute(Request rq) {
        String key = (String) rq.attachments.get("key").get();
        ArrayList<String> messages = new ArrayList<>();
        try {
            messages.add("Элемент [" + manager.removeKey(key) + "] удалён");

        } catch (NotFoundException ex) {
            messages.add("Элемент не существует");
        }
        catch(IllegalDataAccessException ex){
            messages.add("У вас нет доступа к данному элементу");
        }


        return new Response(Response.Status.OK, messages, new HashMap<>());

    }


}
