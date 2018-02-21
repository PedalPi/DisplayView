package io.github.pedalpi.displayview;

import com.google.gson.JsonElement;

public class Data {

    private static Data instance;

    private JsonElement currentPedalboard;

    private int number;

    private JsonElement plugins;

    public static synchronized Data getInstance() {
        if (instance != null)
            return instance;

        Data.instance = new Data();

        return instance;
    }

    public JsonElement getCurrentPedalboard() {
        return currentPedalboard;
    }

    public void setCurrentPedalboard(JsonElement currentPedalboard) {
        this.currentPedalboard = currentPedalboard;
    }

    public int getCurrentPedalboardPosition() {
        return number;
    }

    public void setCurrentPedalboardPosition(int number) {
        this.number = number;
    }

    public JsonElement getPlugins() {
        return plugins;
    }

    public void setPlugins(JsonElement plugins) {
        this.plugins = plugins;
    }
}
