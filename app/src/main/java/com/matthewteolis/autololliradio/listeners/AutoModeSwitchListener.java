package com.matthewteolis.autololliradio.listeners;

import android.widget.CompoundButton;

import com.matthewteolis.autololliradio.AutomationSettings;

public class AutoModeSwitchListener implements CompoundButton.OnCheckedChangeListener {

    private AutomationSettings automationSettings;

    public AutoModeSwitchListener(AutomationSettings automationSettings) {
        this.automationSettings = automationSettings;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        this.automationSettings.setAutoMode(isChecked);
    }
}
