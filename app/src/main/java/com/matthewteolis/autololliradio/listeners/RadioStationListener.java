package com.matthewteolis.autololliradio.listeners;

import android.view.View;
import android.widget.AdapterView;

import com.matthewteolis.autololliradio.CustomMediaPlayer;
import com.matthewteolis.autololliradio.RadioStation;

public class RadioStationListener implements AdapterView.OnItemSelectedListener {

    private CustomMediaPlayer mediaPlayer;

    public RadioStationListener(CustomMediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        this.mediaPlayer.setRadioStation((RadioStation) adapterView.getSelectedItem());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
