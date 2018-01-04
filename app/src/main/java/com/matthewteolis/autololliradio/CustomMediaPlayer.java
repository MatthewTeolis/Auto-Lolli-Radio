package com.matthewteolis.autololliradio;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.Spinner;

import java.io.IOException;

public class CustomMediaPlayer extends MediaPlayer {

    private RadioStation radioStation;

    public CustomMediaPlayer() {

    }

    public void setRadioStation(RadioStation radioStation) {
        this.radioStation = radioStation;
    }

    public RadioStation getRadioStation() {
        return this.radioStation;
    }

    @Override
    public void stop() throws IllegalStateException {
        super.stop();
        this.reset();
    }

    public void play() {
        this.playURL(this.radioStation.getUrl());
    }

    private void playURL(String url) {
        this.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            this.reset();
            this.setDataSource(url);
            this.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.start();
    }
}
