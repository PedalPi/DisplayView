package io.github.pedalpi.pedalpi_display.communication.server;

import org.json.JSONException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import io.github.pedalpi.pedalpi_display.communication.Client;
import io.github.pedalpi.pedalpi_display.communication.message.request.RequestMessage;
import io.github.pedalpi.pedalpi_display.communication.message.request.Messages;
import io.github.pedalpi.pedalpi_display.communication.message.request.SystemMessages;
import io.github.pedalpi.pedalpi_display.communication.message.response.ResponseMessage;

public class Server {
    public interface OnMessageListener {
        void onMessage(ResponseMessage message) throws JSONException;
    }

    private static Server instance;

    public static synchronized Server getInstance() {
        if (instance != null)
            return instance;

        Server.instance = new Server();

        return Server.instance;
    }

    private ServerSocket connection;
    private List<Client> clients = new LinkedList<>();

    private OnMessageListener listener;

    private Transmission transmission;

    private Server() {
        listener = new OnMessageListener() {public void onMessage(ResponseMessage message) {}};
    }

    public void start(int port) {
        try {
            this.connection = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);//e.printStackTrace();
        }

        transmission = new Transmission(this);
        new Thread(transmission).start();
    }

    public void close() {
        transmission.close();
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
        client.send(SystemMessages.CONNECTED);
        client.send(Messages.CURRENT_PEDALBOARD);

        this.clients.add(client);
    }

    public void send(RequestMessage message) {
        for (Client clients : this.clients)
            clients.send(message);
    }

    public void setListener(OnMessageListener listener) {
        this.listener = listener;
    }

    public OnMessageListener getListener() {
        return listener;
    }

    public List<Client> getClients() {
        return clients;
    }
}
