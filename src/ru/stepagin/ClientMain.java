package ru.stepagin;

import ru.stepagin.client.Client;
import ru.stepagin.client.interpreter.Interpreter;
import ru.stepagin.common.io.CommandLineInputManager;
import ru.stepagin.common.io.CommandLineOutManager;

public class ClientMain {

    public static void main(String[] args) {
        // write your code here
        int port;
        if (args.length == 0) {
            port = 13337;
        } else {
            port = Integer.parseInt(args[0]);
        }

        CommandLineInputManager manager = new CommandLineInputManager();

        Client client = new Client();
        client.connect(new byte[]{127, 0, 0, 1}, port);
        if (!client.isConnected()) {
            client.waitConnection();
        }

        Interpreter interpreter = new Interpreter(new CommandLineInputManager(), new CommandLineOutManager(), client);
        interpreter.run();

    }
}
