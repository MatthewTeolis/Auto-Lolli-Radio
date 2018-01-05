package com.matthewteolis.autololliradio.listeners;

import android.view.View;

import com.matthewteolis.autololliradio.CustomMediaPlayer;

public class StopButtonListener implements View.OnClickListener {

    private CustomMediaPlayer mediaPlayer;

    public StopButtonListener(CustomMediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    @Override
    public void onClick(View view) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.stop();
            }
        });
        thread.start();
    }
}
