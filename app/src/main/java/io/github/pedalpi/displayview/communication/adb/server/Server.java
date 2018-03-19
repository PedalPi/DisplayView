package io.github.pedalpi.displayview.communication.adb.server;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import io.github.pedalpi.displayview.communication.adb.Client;
import io.github.pedalpi.displayview.communication.adb.ResponseMessageProcessor;
import io.github.pedalpi.displayview.communication.adb.message.SerialRequestMessage;
import io.github.pedalpi.displayview.communication.adb.message.Identifier;
import io.github.pedalpi.displayview.communication.base.message.Messages;
import io.github.pedalpi.displayview.communication.base.message.RequestMessage;
import io.github.pedalpi.displayview.communication.adb.message.response.ResponseMessage;


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

    private ServerSocket connection;
    private List<Client> clients = new LinkedList<>();

    private ResponseMessageProcessor listener = new ResponseMessageProcessor();
    private Server.OnConnectedListener onConnectedListener = () -> {};
    private Server.OnDisconnectedListener onDisconnectedListener = () -> {};

    public void start(int port) {
        try {
            connection = new ServerSocket(port);
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

        client.send(Messages.PLUGINS);

        this.clients.add(client);
        Log.i("CLIENTS", clients.size() + "");

        onConnectedListener.onConnected();
    }

    public void sendBroadcast(RequestMessage message) {
        for (Client clients : clients) {
            SerialRequestMessage message_clone = SerialRequestMessage.from(message);
            message_clone.setIdentifier(Identifier.instance.next());

            clients.send(message_clone);
        }
    }

    public void setOnMessageListener(Server.OnMessageListener listener) {
        this.listener.setListener(listener);
    }

    public void setOnConnectedListener(Server.OnConnectedListener onConnectedListener) {
        this.onConnectedListener = onConnectedListener;
    }

    public void setOnDisconnectedListener(OnDisconnectedListener onDisconnectedListener) {
        this.onDisconnectedListener = onDisconnectedListener;
    }
}
