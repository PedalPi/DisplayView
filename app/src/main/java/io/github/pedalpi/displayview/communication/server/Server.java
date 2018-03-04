package io.github.pedalpi.displayview.communication.server;

import android.util.Log;

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
import io.github.pedalpi.displayview.communication.message.response.ResponseMessage;


public class Server {

    public interface OnMessageListener {
        void onMessage(ResponseMessage message);
    }

    public interface OnConnectedListener {
        void onConnected();
    }

    public interface OnDisconnectedListener {
        void onDisconnected();
    }

    private static final Server instance = new Server();

    private ServerSocket connection;
    private List<Client> clients = new LinkedList<>();

    private ResponseMessageProcessor listener = new ResponseMessageProcessor();
    private Server.OnConnectedListener onConnectedListener = () -> {};
    private Server.OnDisconnectedListener onDisconnectedListener = () -> {};

    public static Server getInstance() {
        return instance;
    }

    public void start(int port) {
        try {
            instance.connection = new ServerSocket(port);
        } catch (IOException e) {
            Log.e("ERROR", e.getMessage());
            throw new RuntimeException(e);//e.printStackTrace();
        }
    }

    public void close() {
        try {
            for (Client client : clients) {
                client.disconnect();
                clients.remove(client);
            }

            connection.close();
        } catch (IOException e) {
            Log.e("ERROR", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void waitClient() throws IOException {
        Socket socket = connection.accept();

        Client client = new Client(socket);
        client.setOnMessageListener(listener);

        client.setOnDisconnectedListener(() -> {
            clients.remove(client);
            onDisconnectedListener.onDisconnected();
        });

        new Thread(client.listen()).start();

        client.send(SystemMessages.CONNECTED);
        client.send(Messages.PLUGINS);

        this.clients.add(client);
        Log.i("CLIENTS", clients.size() + "");

        onConnectedListener.onConnected();
    }

    public static void sendBroadcast(RequestMessage message) {
        for (Client clients : instance.clients) {
            RequestMessage message_clone = message.clone();
            message_clone.setIdentifier(Identifier.instance.next());

            clients.send(message_clone);
        }
    }

    public static void setOnMessageListener(Server.OnMessageListener listener) {
        instance.listener.setListener(listener);
    }

    public static void setOnConnectedListener(Server.OnConnectedListener onConnectedListener) {
        instance.onConnectedListener = onConnectedListener;
    }

    public static void setOnDisconnectedListener(OnDisconnectedListener onDisconnectedListener) {
        instance.onDisconnectedListener = onDisconnectedListener;
    }
}
