package com.matthewteolis.autololliradio.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.matthewteolis.autololliradio.CustomMediaPlayer;

public class PhoneReceiver extends BroadcastReceiver {

    private CustomMediaPlayer mediaPlayer;
    private boolean wasPlaying;

    public PhoneReceiver(CustomMediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
        wasPlaying = false;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                wasPlaying = true;
            }
        } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            if (wasPlaying) {
                mediaPlayer.play();
                wasPlaying = false;
            }
        } else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                wasPlaying = true;
            }
        } else {
            Toast.makeText(context, "idk what is happening...", Toast.LENGTH_LONG).show();
        }
    }
}
