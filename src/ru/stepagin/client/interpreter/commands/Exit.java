package ru.stepagin.client.interpreter.commands;

import ru.stepagin.client.interpreter.Interpreter;

public class Exit extends InterpreterCommand {
    public Exit(Interpreter interpreter) {
        super(interpreter);
    }

    public void execute() {
        this.interpreter.exit();

    }

    public String info() {
        return "прерывание работы интерпретатора";
    }
}
