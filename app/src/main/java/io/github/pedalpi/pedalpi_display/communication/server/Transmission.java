package io.github.pedalpi.pedalpi_display.communication.server;

import org.json.JSONException;

import java.io.IOException;

import io.github.pedalpi.pedalpi_display.communication.Client;
import io.github.pedalpi.pedalpi_display.communication.MessageBuilder;
import io.github.pedalpi.pedalpi_display.communication.message.response.ResponseMessage;
import io.github.pedalpi.pedalpi_display.communication.message.response.ResponseVerb;

public class Transmission implements Runnable {

    private boolean status;
    private Server server;

    public Transmission(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        this.status = true;

        while (status) {
            ResponseMessage message = getMessage();
            if (message != null)
                try {
                    this.server.getListener().onMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }

    private ResponseMessage getMessage() {
        if (server.getClients().isEmpty())
            return null;

        Client client = server.getClients().get(0);

        try {
            String data = client.getStreamLine();
            if (data == null) {
                //Log.i("DATA", "String empty");
                return null;
            }

            return MessageBuilder.INSTANCE.generate(data);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseMessage(ResponseVerb.ERROR, "{'message': 'error: " +e.getMessage()+ "'}");
        }
    }

    public void close() {
        this.status = false;
    }
}
