package com.blogspot.cartonsbase.chargetogether;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Home extends Activity {

    BluetoothManager bluetoothManager;
    BluetoothAdapter bluetoothAdapter;
    IntentFilter intentFilter;

    ArrayAdapter<String>provider_list;
    ListView ltv_provider;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );

        provider_list = new ArrayAdapter< String >(this, android.R.layout.simple_list_item_1 );
        ltv_provider = (ListView)findViewById( R.id.ltv_provider_list );
        provider_list.add( "1" );
        ltv_provider.setAdapter( provider_list );

        intentFilter = new IntentFilter( BluetoothDevice.ACTION_ACL_CONNECTED );
        this.registerReceiver( receiver, intentFilter );


    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive( Context context, Intent intent ) {
            String action = intent.getAction();
            if ( BluetoothDevice.ACTION_FOUND.equals( action ) ){
                BluetoothDevice device = intent.getParcelableExtra( BluetoothDevice.EXTRA_DEVICE );

                if ( device.getBondState() != BluetoothDevice.BOND_BONDED ){
                    short rssi = intent.getExtras().getShort( BluetoothDevice.EXTRA_RSSI );
                    Toast.makeText( getApplicationContext(), String.valueOf( rssi ), Toast.LENGTH_SHORT ).show();
                }
            }
        }
    };

}
