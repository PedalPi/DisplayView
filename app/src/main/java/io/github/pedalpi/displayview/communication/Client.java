package io.github.pedalpi.displayview.communication;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import io.github.pedalpi.displayview.communication.message.Identifier;
import io.github.pedalpi.displayview.communication.message.request.RequestMessage;
import io.github.pedalpi.displayview.communication.message.request.RequestVerb;
import io.github.pedalpi.displayview.communication.message.response.ResponseMessage;

public class Client {

    public interface OnMessageListener {
        void onMessage(ResponseMessage message);
    }

    private boolean open = false;

    private Socket connection;
    private PrintStream out;
    private BufferedReader in;

    private OnMessageListener onMessageListener = message -> {};

    public Client(Socket connection) {
        try {
            this.connection = connection;
            this.out = new PrintStream(this.connection.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Runnable lissen() {
        open = true;

        return () -> {
            while (open) {
                ResponseMessage message = getMessage();
                if (message != null)
                    this.onMessageListener.onMessage(message);
            }
        };
    }

    private ResponseMessage getMessage() {
        try {
            String data = getStreamLine();
            if (data == null)
                return null;

            return MessageBuilder.INSTANCE.generate(data);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseMessage.error(e.getMessage());
        }
    }

    public void disconnect() throws IOException {
        open = false;

        connection.close();
        out.close();
        in.close();
    }

    public void send(RequestMessage message) {
        if (message.getType() != RequestVerb.SYSTEM && message.getType() != RequestVerb.NIL)
            Identifier.instance.register(message);

        Log.i("Request", message.toString());
        out.print(message.toString());
    }

    private String getStreamLine() throws IOException {
        return in.readLine();
    }

    public void setOnMessageListener(OnMessageListener onMessageListener) {
        this.onMessageListener = onMessageListener;
    }
}
