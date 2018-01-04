package com.matthewteolis.autololliradio.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.matthewteolis.autololliradio.R;
import com.matthewteolis.autololliradio.RadioStation;

public class RadioStationSpinnerAdapter extends BaseAdapter {

    private RadioStation[] radioStations;
    private LayoutInflater inflter;

    public RadioStationSpinnerAdapter(Context applicationContext, RadioStation[] radioStations) {
        this.radioStations = radioStations;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return radioStations.length;
    }

    @Override
    public Object getItem(int i) {
        return radioStations[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.radio_stations_spinner_items, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView);
        icon.setImageResource(radioStations[i].getIcon());
        names.setText(radioStations[i].getName());
        return view;
    }
}
