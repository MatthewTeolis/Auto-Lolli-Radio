package com.matthewteolis.autololliradio.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.matthewteolis.autololliradio.CustomMediaPlayer;

public class NetworkReceiver extends BroadcastReceiver {

    private CustomMediaPlayer mediaPlayer;

    public NetworkReceiver(CustomMediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (isConnected(connectivityManager)) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.play();
            }
        }
    }

    private boolean isConnected(ConnectivityManager connectivityManager) {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
