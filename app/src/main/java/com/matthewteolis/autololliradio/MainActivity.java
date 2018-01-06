package com.matthewteolis.autololliradio;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.matthewteolis.autololliradio.adapters.BluetoothSpinnerAdapter;
import com.matthewteolis.autololliradio.adapters.RadioStationSpinnerAdapter;
import com.matthewteolis.autololliradio.listeners.AutoModeSwitchListener;
import com.matthewteolis.autololliradio.listeners.BluetoothSpinnerListener;
import com.matthewteolis.autololliradio.listeners.PlayButtonListener;
import com.matthewteolis.autololliradio.listeners.RadioStationListener;
import com.matthewteolis.autololliradio.listeners.StopButtonListener;
import com.matthewteolis.autololliradio.receivers.BluetoothConnectedReceiver;
import com.matthewteolis.autololliradio.receivers.BluetoothDisconnectedReceiver;
import com.matthewteolis.autololliradio.receivers.NetworkReceiver;
import com.matthewteolis.autololliradio.receivers.PhoneReceiver;

public class MainActivity extends AppCompatActivity {

    private BluetoothConnectedReceiver bluetoothConnectedReceiver;
    private BluetoothDisconnectedReceiver bluetoothDisconnectedReceiver;
    private PhoneReceiver phoneReceiver;
    private NetworkReceiver networkReceiver;

    private static final int REQUEST_ENABLE_BT = 1;

    private CustomMediaPlayer mediaPlayer;
    private AutomationSettings automationSettings;

    private SharedPreferences sharedPreferences;

    private Spinner bluetoothSpinner;
    private Spinner radioSpinner;
    private Switch autoSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = new CustomMediaPlayer();
        automationSettings = new AutomationSettings();
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        initRadioSpinner();
        initAutoModeSwitch();
        initPlayButton();
        initStopButton();

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            // Display a message that the app is not meant for them
            // TODO: display message showing you can't use the app...
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
            initBluetoothSpinner(mBluetoothAdapter);
            initBluetoothReceiver();
        }

        if (!hasPhonePermission()) {
            requestPhonePermission();
        } else {
            initPhoneReceiver();
        }

        initNetworkReceiver();
    }

    private void initBluetoothReceiver() {
        bluetoothConnectedReceiver = new BluetoothConnectedReceiver(mediaPlayer, automationSettings);
        bluetoothDisconnectedReceiver = new BluetoothDisconnectedReceiver(mediaPlayer, automationSettings);

        registerReceiver(bluetoothConnectedReceiver, new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED));
        registerReceiver(bluetoothDisconnectedReceiver, new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED));
    }

    private void initNetworkReceiver() {
        networkReceiver = new NetworkReceiver(mediaPlayer);
        registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void initPhoneReceiver() {
        phoneReceiver = new PhoneReceiver(mediaPlayer);
        registerReceiver(phoneReceiver, new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED));
    }

    private boolean hasPhonePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPhonePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PermissionCodes.READ_PHONE_STATE);
    }

    private void initBluetoothSpinner(BluetoothAdapter mBluetoothAdapter) {
        int selectedIndex = sharedPreferences.getInt(getString(R.string.preference_bonded_bluetooth_devices_spinner_index), 0);

        bluetoothSpinner = findViewById(R.id.bondedBluetoothDevicesSpinner);
        BluetoothDevice[] bondedBluetoothDevices = mBluetoothAdapter.getBondedDevices().toArray(new BluetoothDevice[0]);
        BluetoothSpinnerAdapter bluetoothSpinnerAdapter = new BluetoothSpinnerAdapter(this.getApplicationContext(), bondedBluetoothDevices);
        bluetoothSpinner.setAdapter(bluetoothSpinnerAdapter);
        bluetoothSpinner.setSelection(selectedIndex);
        bluetoothSpinner.setOnItemSelectedListener(new BluetoothSpinnerListener(automationSettings));

        automationSettings.setBluetoothDevice((BluetoothDevice) bluetoothSpinner.getSelectedItem());
    }

    private void initPlayButton() {
        ImageButton playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new PlayButtonListener(mediaPlayer));
    }

    private void initStopButton() {
        ImageButton stopButton = findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new StopButtonListener(mediaPlayer));
    }

    private void initRadioSpinner() {
        RadioStation happyStation = new RadioStation("Lolli Radio - Happy", "http://nr11.newradio.it:8050/stream.mp3", R.drawable.lolli_logo_happy);
        RadioStation italiaStation = new RadioStation("Lolli Radio - Italia", "http://sr6.inmystream.info:8008/;listen&ext=.mp3", R.drawable.lolli_logo_italia);
        RadioStation softStation = new RadioStation("Lolli Radio - Soft", "http://sr6.inmystream.info:8010/;listen&ext=.mp3", R.drawable.lolli_logo_soft);
        RadioStation danceStation = new RadioStation("Lolli Radio - Dance", "http://sr6.inmystream.info:8012/;listen&ext=.mp3", R.drawable.lolli_logo_dance);
        RadioStation hitsStation = new RadioStation("Lolli Radio - Hits", "http://nr11.newradio.it:8080/stream.mp3", R.drawable.lolli_logo_hits);
        RadioStation oldiesStation = new RadioStation("Lolli Radio - Oldies", "http://nr11.newradio.it:8070/stream.mp3", R.drawable.lolli_logo_oldies);

        int selectedIndex = sharedPreferences.getInt(getString(R.string.preference_radio_station_spinner_index), 0);

        radioSpinner = findViewById(R.id.radioStationSpinner);
        RadioStation[] radioStations = {softStation, happyStation, italiaStation, danceStation, hitsStation, oldiesStation};
        RadioStationSpinnerAdapter radioStationSpinnerAdapter = new RadioStationSpinnerAdapter(this.getApplicationContext(), radioStations);
        radioSpinner.setAdapter(radioStationSpinnerAdapter);
        radioSpinner.setSelection(selectedIndex);
        radioSpinner.setOnItemSelectedListener(new RadioStationListener(mediaPlayer));
    }

    private void initAutoModeSwitch() {
        boolean isChecked = sharedPreferences.getBoolean(getString(R.string.preference_auto_mode_switch), true);

        autoSwitch = findViewById(R.id.autoSwitch);
        autoSwitch.setChecked(isChecked);
        autoSwitch.setOnCheckedChangeListener(new AutoModeSwitchListener(automationSettings));

        automationSettings.setAutoMode(isChecked);
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(getString(R.string.preference_bonded_bluetooth_devices_spinner_index), bluetoothSpinner.getSelectedItemPosition());
        editor.putInt(getString(R.string.preference_radio_station_spinner_index), radioSpinner.getSelectedItemPosition());
        editor.putBoolean(getString(R.string.preference_auto_mode_switch), autoSwitch.isChecked());

        editor.apply();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (sharedPreferences != null) {
            savePreferences();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PermissionCodes.READ_PHONE_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initPhoneReceiver();
                }
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bluetoothConnectedReceiver);
        unregisterReceiver(bluetoothDisconnectedReceiver);
        unregisterReceiver(phoneReceiver);
        unregisterReceiver(networkReceiver);
    }
}
