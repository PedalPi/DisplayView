package io.github.pedalpi.pedalpi_display.communication.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import io.github.pedalpi.pedalpi_display.communication.Client;
import io.github.pedalpi.pedalpi_display.communication.message.request.Messages;
import io.github.pedalpi.pedalpi_display.communication.message.request.RequestMessage;
import io.github.pedalpi.pedalpi_display.communication.message.request.SystemMessages;

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

    private Client.OnMessageListener listener = message -> {};

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

        this.clients.add(client);
    }

    public void sendBroadcast(RequestMessage message) {
        for (Client clients : this.clients)
            clients.send(message);
    }

    public void setListener(Client.OnMessageListener listener) {
        this.listener = listener;

        for (Client client: clients)
            client.setOnMessageListener(listener);
    }
}
