package com.blogspot.cartonsbase.chargetogether;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Home extends Activity implements LocationListener {
    private static final String TAG = "Home Debugger";

    ArrayAdapter< String > provider_list;
    ListView ltv_provider;

    String UserName;

    double latitude;    //GPSx
    double longitude;   //GPSy
    private String bestGPSProvider = LocationManager.GPS_PROVIDER;
    LocationManager locationManager;
    boolean GPSisOPEN = false;
    Location location;
    @Override
    @TargetApi(23)
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );

        provider_list = new ArrayAdapter< String >( this, android.R.layout.simple_list_item_1 );
        ltv_provider = ( ListView ) findViewById( R.id.ltv_provider_list );
        provider_list.add( "1" );
        ltv_provider.setAdapter( provider_list );

        Bundle bundle = this.getIntent().getExtras();
        UserName = bundle.getString( "UserName" );

        locationManager = ( LocationManager ) getSystemService( Context
                .LOCATION_SERVICE );
        if ( locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ||
                locationManager.isProviderEnabled( LocationManager.NETWORK_PROVIDER ) ) {
            GPSisOPEN = true;
        //    GPSSearch();
        }


    }

    public void GPSSearch() {
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return  ;
        }
        Criteria criteria = new Criteria();
        bestGPSProvider = locationManager.getBestProvider( criteria, true );

        location = locationManager.getLastKnownLocation( bestGPSProvider );

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        Toast.makeText( getApplicationContext(), "Latitude: " + latitude + "\nLongitude" + longitude, Toast.LENGTH_SHORT ).show();
        Log.d( TAG, "Latitude: " + latitude + "\nLongitude" + longitude );
    }

    @Override
    public void onLocationChanged( Location location ) {
        //Toast.makeText( getApplicationContext(), "Location Update: " + "\nLatitude: " + latitude + "\nLongitude" + longitude, Toast.LENGTH_SHORT ).show();
        GPSSearch();
    }

    @Override
    public void onStatusChanged( String provider, int status, Bundle extras ) {

    }

    @Override
    public void onProviderEnabled( String provider ) {

    }

    @Override
    public void onProviderDisabled( String provider ) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if ( GPSisOPEN ) {
            if ( Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return  ;
            }
            locationManager.requestLocationUpdates( bestGPSProvider, 1000, 1, this );

            /*Timer timer = new Timer(  );
            timer.schedule( new TimerTask() {
                @Override
                public void run() {

                }
            }, 5000 );*/

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if ( GPSisOPEN ) {
            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
                if ( checkSelfPermission( Manifest.permission.ACCESS_FINE_LOCATION ) !=
                        PackageManager.PERMISSION_GRANTED && checkSelfPermission( Manifest.permission
                        .ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                    return;
                }
            }
            locationManager.removeUpdates( this );
        }
    }
}
