package ru.stepagin.client.interpreter.commands;

import ru.stepagin.client.interpreter.Interpreter;
import ru.stepagin.common.dataTransfer.DataTransference;
import ru.stepagin.common.dataTransfer.Request;
import ru.stepagin.common.dataTransfer.Response;
import ru.stepagin.common.dataTransfer.Utils;
import ru.stepagin.common.lab.Worker;

import java.util.HashMap;


public class RemoveGreater extends InterpreterCommand {
    public RemoveGreater(Interpreter interpreter) {
        super(interpreter);
    }

    public void execute() {
        if (inputManager.getWords().size() < 2) {
            outputManager.println("Неверное количество аргументов");
            return;
        }
        String sal = inputManager.getWords().get(1);
        if (Worker.Params.salary.parse(sal)) {
            Float salary = Worker.Params.salary.get();
            HashMap<String, DataTransference<?>> arguments = new HashMap<>();
            arguments.put("salary", new DataTransference<Float>(Float.class, salary));
            Request rq = new Request("remove_greater", arguments);
            if (client.sendRequest(rq)) {
                Response resp = client.receive();
                Utils.printResponseMessages(resp);
            }
        } else {
            outputManager.println("Синтаксическая ошибка. проверьте корректность аргументов");
            return;
        }


    }

    @Override
    public String info() {
        return "Удаляет всех рабочих, ЗП которых превышает заданную. remove_greater <salary>";
    }
}
