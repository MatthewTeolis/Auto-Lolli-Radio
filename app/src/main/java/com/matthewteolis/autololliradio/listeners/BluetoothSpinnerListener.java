package com.matthewteolis.autololliradio.listeners;

import android.bluetooth.BluetoothDevice;
import android.view.View;
import android.widget.AdapterView;

import com.matthewteolis.autololliradio.AutomationSettings;

public class BluetoothSpinnerListener implements AdapterView.OnItemSelectedListener {

    private AutomationSettings automationSettings;

    public BluetoothSpinnerListener(AutomationSettings automationSettings) {
        this.automationSettings = automationSettings;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        this.automationSettings.setBluetoothDevice((BluetoothDevice) adapterView.getSelectedItem());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
