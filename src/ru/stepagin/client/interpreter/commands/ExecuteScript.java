package ru.stepagin.client.interpreter.commands;


import ru.stepagin.client.interpreter.Interpreter;
import ru.stepagin.common.io.FileInputManager;
import ru.stepagin.common.io.InputManager;
import ru.stepagin.common.io.OutputManager;
import ru.stepagin.common.io.VoidOutputManager;

import java.io.File;
import java.io.IOException;

public class ExecuteScript extends InterpreterCommand {
    public ExecuteScript(Interpreter interpreter) {
        super(interpreter);
    }

    public void execute() {
        InputManager inputManager = this.interpreter.getInputManager();
        OutputManager outputManager = this.interpreter.getOutputManager();
        if (inputManager.getWords().size() < 2) {
            outputManager.println("Неверное количество аргументов");
            return;
        }
        String path = inputManager.getWords().get(1);
        try {
            Interpreter exe_inter = new Interpreter(new FileInputManager(new File(path)), new VoidOutputManager(), this.client);
            exe_inter.run();
        } catch (IOException ex) {
            outputManager.println("Скрипт или файл сломался :(");
        }
    }

    public String info() {
        return "исполнить скрипт. script_execute <path>";
    }
}
