package com.matthewteolis.autololliradio;

import android.bluetooth.BluetoothDevice;

public class AutomationSettings {

    private BluetoothDevice bluetoothDevice;
    private boolean autoMode;

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public BluetoothDevice getBluetoothDevice() {
        return this.bluetoothDevice;
    }

    public void setAutoMode(boolean autoMode) {
        this.autoMode = autoMode;
    }

    public boolean isAutoMode() {
        return this.autoMode;
    }
}
