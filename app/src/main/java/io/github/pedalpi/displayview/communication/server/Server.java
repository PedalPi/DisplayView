package io.github.pedalpi.displayview.communication.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import io.github.pedalpi.displayview.communication.Client;
import io.github.pedalpi.displayview.communication.ResponseMessageProcessor;
import io.github.pedalpi.displayview.communication.message.Identifier;
import io.github.pedalpi.displayview.communication.message.request.Messages;
import io.github.pedalpi.displayview.communication.message.request.RequestMessage;
import io.github.pedalpi.displayview.communication.message.request.SystemMessages;

public class Server {
    private static Server instance;

    public static synchronized Server getInstance() {
        if (instance != null)
            return instance;

        Server.instance = new Server();

        return Server.instance;
    }

    private ServerSocket connection;
    private List<Client> clients = new LinkedList<>();

    private ResponseMessageProcessor listener = new ResponseMessageProcessor();

    public void start(int port) {
        try {
            this.connection = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);//e.printStackTrace();
        }
    }

    public void close() {
        try {
            for (Client client : clients)
                client.disconnect();

            connection.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void waitClient() throws IOException {
        Socket socket = connection.accept();

        Client client = new Client(socket);
        client.setOnMessageListener(listener);

        new Thread(client.lissen()).start();

        client.send(SystemMessages.CONNECTED);
        client.send(Messages.CURRENT_PEDALBOARD_DATA);
        client.send(Messages.PLUGINS);

        this.clients.add(client);
    }

    public void sendBroadcast(RequestMessage message) {
        for (Client clients : this.clients) {
            RequestMessage message_clone = message.clone();
            message_clone.setIdentifier(Identifier.instance.next());

            clients.send(message_clone);
        }
    }

    public void setListener(Client.OnMessageListener listener) {
        this.listener.setListener(listener);
    }
}
