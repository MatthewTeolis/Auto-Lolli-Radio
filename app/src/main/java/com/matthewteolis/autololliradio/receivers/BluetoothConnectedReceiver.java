package com.matthewteolis.autololliradio.receivers;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Spinner;
import android.widget.Switch;

import com.matthewteolis.autololliradio.AutomationSettings;
import com.matthewteolis.autololliradio.CustomMediaPlayer;

public class BluetoothConnectedReceiver extends BroadcastReceiver {

    private CustomMediaPlayer mediaPlayer;
    private AutomationSettings automationSettings;

    public BluetoothConnectedReceiver(CustomMediaPlayer mediaPlayer, AutomationSettings automationSettings) {
        this.mediaPlayer = mediaPlayer;
        this.automationSettings = automationSettings;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        BluetoothDevice remoteDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        BluetoothDevice selectedDevice = automationSettings.getBluetoothDevice();

        if (automationSettings.isAutoMode() && selectedDevice.getAddress().equals(remoteDevice.getAddress())) {
            this.mediaPlayer.play();
        }
    }
}
