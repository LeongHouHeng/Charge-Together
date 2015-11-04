package com.blogspot.cartonsbase.chargetogether;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.blogspot.cartonsbase.chargetogether.Network.ContactServer;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Timer;
import java.util.TimerTask;

public class Home extends FragmentActivity implements LocationListener, OnMapReadyCallback {
    private static final String TAG = "Home Debugger";

    //ArrayAdapter< String > provider_list;
    //ListView ltv_provider;

    String UserName = "User";

    double latitude;    //GPSx
    double longitude;   //GPSy
    private String bestGPSProvider = LocationManager.GPS_PROVIDER;
    LocationManager locationManager;
    boolean GPSisOPEN = false;
    Location location;

    //Button btn_showflag;

    Fragment infoFragment;
    FragmentManager fragmentManager;

    GoogleMap map;
    MapFragment fragment_map;

    boolean fragmentIsShowed = false;

    @Override
    @TargetApi( 23 )
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );

        /*provider_list = new ArrayAdapter< String >( this, android.R.layout.simple_list_item_1 );
        ltv_provider = ( ListView ) findViewById( R.id.ltv_provider_list );
        provider_list.add( "1" );
        ltv_provider.setAdapter( provider_list );*/
        infoFragment = new InfoWindowFragment();

        locationManager = ( LocationManager ) getSystemService( Context
                .LOCATION_SERVICE );
        if ( locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ||
                locationManager.isProviderEnabled( LocationManager.NETWORK_PROVIDER ) ) {
            GPSisOPEN = true;
            //    GPSSearch();
        }
        fragment_map = (MapFragment) getFragmentManager().findFragmentById( R.id.frag_map );
        fragment_map.getMapAsync( this );

        /*btn_showflag = (Button)findViewById( R.id.btn_showFrag );
        btn_showflag.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

                fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add( R.id.activity_home, infoFragment, "first" );
                fragmentTransaction.setCustomAnimations( R.transition.tween, R.transition.no_change,
                        R.transition.tween, R.transition.no_change );
                fragmentTransaction.commit();

            }
        } );*/


    }

    public void GPSSearch() {
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            return;
        }
        Criteria criteria = new Criteria();
        bestGPSProvider = locationManager.getBestProvider(criteria, true);

        location = locationManager.getLastKnownLocation(bestGPSProvider);

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        Toast.makeText( getApplicationContext(), "Latitude: " + latitude + "\nLongitude" + longitude, Toast.LENGTH_SHORT ).show();
        Log.d(TAG, "Latitude: " + latitude + "\nLongitude" + longitude);

        /*LatLng IamHere = new LatLng(latitude, longitude );
        map.addMarker( new MarkerOptions().position( IamHere ).title( "I am Here" ).snippet
                ( "and" + " snippet" ).icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory
                .HUE_BLUE ) ) );
        map.moveCamera( CameraUpdateFactory.newLatLngZoom( IamHere, 15.0f ) );*/

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

            /*ltv_provider.setOnItemClickListener( new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick( AdapterView< ? > parent, View view, int position, long
                        id ) {
                    ContactServer contactServer = new ContactServer(getApplicationContext());
                    contactServer.getHelp( latitude, longitude );
                }
            });*/
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

    @Override
    public void onMapReady( GoogleMap googleMap ) {
        //Toast.makeText( getApplicationContext(), "onMapReady", Toast.LENGTH_SHORT ).show();
        map = googleMap;
        LatLng IamHere = new LatLng(latitude, longitude );
        map.addMarker( new MarkerOptions().position( IamHere ).title( "I am Here" ).snippet
                ( "and" + " snippet" ).icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory
                .HUE_BLUE ) ) );
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(IamHere, 15.0f));

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Click Marker");
                if (fragmentIsShowed == false) {
                    Bundle bundle = new Bundle();
                    bundle.putString("UserName", UserName);
                    bundle.putDouble("Latitude", latitude);
                    bundle.putDouble("Longitude", longitude);
                    infoFragment.setArguments(bundle);
                    fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.activity_home, infoFragment, "first");
                    fragmentTransaction.commit();
                    fragmentIsShowed = true;

                } else {
                    fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.remove(infoFragment);
                    fragmentTransaction.commit();
                    fragmentIsShowed = false;
                }

                return true;
            }
        });

    }


}
