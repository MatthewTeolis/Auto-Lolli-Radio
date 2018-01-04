package com.matthewteolis.autololliradio;

public class RadioStation {
    private String name;
    private String url;
    private int icon;

    public RadioStation(String name, String url, int icon) {
        this.name = name;
        this.url = url;
        this.icon = icon;
    }

    public String getName() {
        return this.name;
    }

    public String getUrl() {
        return this.url;
    }

    public int getIcon() {
        return this.icon;
    }
}
