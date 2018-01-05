package com.matthewteolis.autololliradio.listeners;

import android.view.View;

import com.matthewteolis.autololliradio.CustomMediaPlayer;

public class PlayButtonListener implements View.OnClickListener {

    private CustomMediaPlayer mediaPlayer;

    public PlayButtonListener(CustomMediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    @Override
    public void onClick(View view) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.play();
            }
        });
        thread.start();
    }
}
