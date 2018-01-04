package com.matthewteolis.autololliradio.adapters;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BluetoothSpinnerAdapter extends BaseAdapter {

    private BluetoothDevice[] bluetoothDevices;
    private LayoutInflater inflter;

    public BluetoothSpinnerAdapter(Context applicationContext, BluetoothDevice[] bluetoothDevices) {
        this.bluetoothDevices = bluetoothDevices;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return bluetoothDevices.length;
    }

    @Override
    public Object getItem(int i) {
        return bluetoothDevices[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(android.R.layout.simple_list_item_1, null);
        TextView names = (TextView) view.findViewById(android.R.id.text1);
        names.setText(bluetoothDevices[i].getName());
        names.setTextColor(Color.BLACK);
        return view;
    }
}
