package ru.stepagin.server;

import ru.stepagin.common.Utils.ObjectSerializer;
import ru.stepagin.common.dataTransfer.Request;
import ru.stepagin.common.dataTransfer.Response;
import ru.stepagin.server.responseInterpreter.Interpreter;
import ru.stepagin.server.workersManager.WorkersManager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.*;

class ServerCommandController extends Thread {
    Server server;
    boolean stopped = false;

    public ServerCommandController(Server server) {
        this.server = server;
    }

    private void save() {
        try {
            this.server.manager.dump();
            System.out.println("Собрано в файл");
        } catch (IOException ex) {
            System.out.println("ошибка при работе с файлом");
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        try {
            while (!stopped && scanner.hasNext()) {

                String command = scanner.nextLine();
                switch (command) {
                    case "exit":
                        save();
                        this.server.stop();
                        scanner.close();
                        stopped = false;
                        Thread.currentThread().interrupt();
                        break;
                    case "save":
                        save();
                        break;
                    default:
                        System.out.println("Команда не распознана");
                }

            }


        } catch (IllegalStateException ex) {
            Thread.currentThread().interrupt();
        }
    }
}

public class Server {
    private Selector selector;
    private final ByteBuffer buffer;
    private ServerSocketChannel serverSocketChannel;
    private boolean stopped = false;
    private final Interpreter interpreter;
    public final WorkersManager manager;
    protected ForkJoinPool requestExecutorPool;
    protected ForkJoinPool responseSenderPool;
    protected ExecutorService requestReaderPool;
    protected SocketChannel connectedTo = null;
    public Server(Interpreter interpreter) {
        this.buffer = ByteBuffer.allocate(10000);
        this.interpreter = interpreter;
        this.manager = this.interpreter.getManager();
        this.requestExecutorPool = ForkJoinPool.commonPool();
        this.responseSenderPool = ForkJoinPool.commonPool();
        this.requestReaderPool = Executors.newCachedThreadPool();
    }

    public synchronized void stop() {
        this.stopped = true;

        try {
            this.selector.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }


    public void bind(int port) throws IOException {
        this.selector = Selector.open();
        SocketAddress address = new InetSocketAddress(port);
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.bind(address);
        this.serverSocketChannel.configureBlocking(false);
        this.serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
//        this.serverSocketChannel.configureBlocking(true);

    }

    private void createConnection() throws IOException {
//        System.out.println("isRegistered: "+(serverSocketChannel.isRegistered()));
//        if (!serverSocketChannel.isRegistered()){
            SocketChannel client = this.serverSocketChannel.accept();
            if (connectedTo != null){
                if (client.isConnected()) {
                    return;
                }
            } else {
                connectedTo = client;
            }
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
//        }
    }

    private Request readRequest(SocketChannel client) throws IOException {


        try {
            buffer.clear();
            client.read(buffer);
            Request rq;
            ByteArrayInputStream stream = new ByteArrayInputStream(buffer.array());
            ObjectInputStream os = new ObjectInputStream(stream);
            rq = (Request) os.readObject();
            buffer.flip();
            return rq;
        } catch (SocketException | ClassNotFoundException ex) {

            client.close();
        } catch (StreamCorruptedException ex) {

            client.close();
            buffer.clear();
        } catch (ClassCastException ex) {
            return null;
        }
        return null;

    }

    private void sendResponse(SocketChannel client, Response resp) throws IOException {
        try {

            byte[] bytes = ObjectSerializer.serialize(resp);
            ReadableByteChannel channel = Channels.newChannel(new ByteArrayInputStream(bytes));

            buffer.clear();
            channel.read(buffer);
            buffer.flip();
            client.write(buffer);
            buffer.flip();
        } catch (ClosedChannelException ex) {
            client.close();

        }


    }

    public void run() {
        ServerCommandController controller = new ServerCommandController(this);
        controller.start();
        buffer.flip();

        while (!this.stopped) {

            //System.out.println("what");
            try {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();

                while (it.hasNext()) {
                    SelectionKey key = it.next();

                    if (key.isValid()) {
                        if (key.isAcceptable()) {
                            this.createConnection();
                        }
                        if (key.isReadable()) {
                            SocketChannel client = (SocketChannel) key.channel();

                            Request rq = this.readRequest(client);

                            if (rq != null) {

                                Response resp = interpreter.execute(rq);
                                this.sendResponse(client, resp);
                            }
                            else{
                                key.channel().close();

                            }

                        }

                    }
                    it.remove();
                }
            } catch (IOException ex) {

                ex.printStackTrace();

            } catch (ClosedSelectorException ex) {
                continue;
            }
        }

    }
}
