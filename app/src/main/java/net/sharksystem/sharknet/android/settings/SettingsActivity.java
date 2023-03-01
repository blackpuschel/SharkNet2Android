package net.sharksystem.sharknet.android.settings;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import net.sharksystem.R;
import net.sharksystem.asap.android.apps.ASAPActivity;
import net.sharksystem.sharknet.android.SharkNetApp;

public class SettingsActivity extends ASAPActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_drawer_layout);

        SharkNetApp.getSharkNetApp().setupDrawerLayout(this);

        // add listener for each setting

        ////////////////////////////////////////////////////////////////////////////
        //                          protocol switch on/off                        //
        ////////////////////////////////////////////////////////////////////////////

        ///////////////// BLUETOOTH
        ToggleButton toggle = (ToggleButton) findViewById(R.id.fragment_network_bluetooth_on_off_button);
        // set initial status
        toggle.setChecked(this.isBluetoothEnvironmentOn());

        // add behaviour
        toggle.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(!dontDoAnything) {
                            if (isChecked) {
                                Log.d(getLogStart(), "ui said: switch on BT");
                                startBluetooth();
                            } else {
                                Log.d(getLogStart(), "ui said: switch off BT");
                                stopBluetooth();
                            }
                        }
                    }
                });

        ///////////////// BLUETOOTH DISCOVERABLE
/*
        toggle = (ToggleButton) findViewById(R.id.settingsBluetoothDiscoverableToggleButton);
        // set initial status
        toggle.setChecked(isBluetoothDiscoverable());

        // add behaviour
        toggle.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(!dontDoAnything) {
                            if (isChecked) {
                                Log.d(getLogStart(), "ui said: switch on BT Discoverable");
                                startBluetoothDiscoverable();
                            } else {
                                Log.d(getLogStart(), "ui said: switch off BT - there is no switch off - yet TODO");
                            }
                        }
                    }
                });

        ///////////////// BLUETOOTH DISCOVERY
        toggle = (ToggleButton) findViewById(R.id.settingsBluetoothDiscoveryToggleButton);
        // set initial status
        toggle.setChecked(isBluetoothDiscovery());

        // add behaviour
        toggle.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (!dontDoAnything) {
                            if (isChecked) {
                                Log.d(getLogStart(), "ui said: switch on BT Discovery");
                                startBluetoothDiscovery();
                            } else {
                                Log.d(getLogStart(), "ui said: switch off BT - TODO nyi");
                                //sharkNetApp.stopBluetooth();
                            }
                        }
                    }
                });
 */
        ///////////////// BLUETOOTH DISCOVERY AND DISCOVERABLE = SCAN BT
        toggle = (ToggleButton) findViewById(R.id.fragment_network_bluetooth_scan_button);
        // set initial status
        toggle.setChecked(isBluetoothDiscovery());

        // add behaviour
        toggle.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (!dontDoAnything) {
                            if (isChecked) {
                                Log.d(getLogStart(), "ui said: switch on BT D and D");
                                startBluetoothDiscovery();
                                startBluetoothDiscoverable();
                            } else {
                                Log.d(getLogStart(), "ui said: switch off BT - TODO nyi");
                                //sharkNetApp.stopBluetooth(); TODO
                            }
                        }
                    }
                });

        ///////////////// ASAP Hub on / off
        toggle = (ToggleButton) findViewById(R.id.fragment_network_bluetooth_connect_hubs_button);
        // set initial status
        toggle.setChecked(isASAPHubsConnected());

        // add behaviour
        toggle.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (!dontDoAnything) {
                            if (isChecked) {
                                Log.d(getLogStart(), "ui said: switch on BT D and D");
                                connectASAPHubs();
                            } else {
                                disconnectASAPHubs();
                            }
                        }
                    }
                });
    }

    private boolean dontDoAnything = false;

    private void refreshToggleButtons() {
        this.dontDoAnything = true;

        ToggleButton toggle = (ToggleButton) findViewById(R.id.fragment_network_bluetooth_on_off_button);
        toggle.setChecked(this.isBluetoothEnvironmentOn());
        toggle.refreshDrawableState();

        /*
        toggle = (ToggleButton) findViewById(R.id.settingsBluetoothDiscoverableToggleButton);
        toggle.setChecked(this.isBluetoothDiscoverable());
        toggle.refreshDrawableState();

        toggle = (ToggleButton) findViewById(R.id.settingsBluetoothDiscoveryToggleButton);
        toggle.setChecked(this.isBluetoothDiscovery());
        toggle.refreshDrawableState();
         */

        toggle = (ToggleButton) findViewById(R.id.fragment_network_bluetooth_scan_button);
        toggle.setChecked(this.isBluetoothDiscovery());
        toggle.refreshDrawableState();

        this.dontDoAnything = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.refreshProtocolStatus();
    }

    public void onConfigASAPHubs(View view) {
        Intent intent = new Intent(this, HubDescriptionsListActivity.class);
        this.startActivity(intent);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    //                             keep in sync with protocol changes                         //
    ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onResume() {
        super.onResume();
        this.refreshProtocolStatus();
    }

    @Override
    public void asapNotifyBTDiscoverableStarted() {
        super.asapNotifyBTDiscoverableStarted();
        this.refreshToggleButtons();
    }

    @Override
    public void asapNotifyBTDiscoverableStopped() {
        super.asapNotifyBTDiscoverableStopped();
        this.refreshToggleButtons();
    }

    @Override
    public void asapNotifyBTEnvironmentStarted() {
        super.asapNotifyBTEnvironmentStarted();
        this.refreshToggleButtons();
    }

    @Override
    public void asapNotifyBTEnvironmentStopped() {
        super.asapNotifyBTEnvironmentStopped();
        this.refreshToggleButtons();
    }

    @Override
    public void asapNotifyBTDiscoveryStarted() {
        super.asapNotifyBTDiscoveryStarted();
        Log.d(this.getLogStart(), "asapNotifyBTDiscoveryStarted() called");
        this.refreshToggleButtons();
    }

    @Override
    public void asapNotifyBTDiscoveryStopped() {
        super.asapNotifyBTDiscoveryStopped();
        this.refreshToggleButtons();
    }
}
