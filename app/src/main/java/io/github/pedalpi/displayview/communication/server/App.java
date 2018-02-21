package io.github.pedalpi.pedalpi_display.communication.server;

import android.app.Application;

import java.io.IOException;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Runnable runnable = this::runServer;

        new Thread(runnable).start();
    }

    private void runServer() {
        Server server = Server.getInstance();
        server.start(8888);

        try {
            while (true)
                server.waitClient();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}