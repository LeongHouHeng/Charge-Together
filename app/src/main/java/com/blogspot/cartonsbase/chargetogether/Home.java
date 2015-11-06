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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import com.blogspot.cartonsbase.chargetogether.Network.JSON.JsonObj;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Home extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = "Home";

    ContactServer contactServer;
    String UserName = "User";

    double latitude;    //GPSx
    double longitude;   //GPSy
    private String bestGPSProvider = LocationManager.GPS_PROVIDER;
    LocationManager locationManager;
    boolean GPSisOPEN = false;
    Location location;

    Fragment infoFragment;
    FragmentManager fragmentManager;

    GoogleMap map;
    MapFragment fragment_map;

    boolean fragmentIsShowed = false;

    boolean MapIsReady = false;
    ArrayList<JsonObj> jsonArr;

    public HashMap<String, Marker> mapHash;

    MyLocation myLocation;

    @Override
    @TargetApi( 23 )
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_home );

        infoFragment = new InfoWindowFragment();
        contactServer = new ContactServer(getApplicationContext());

        mapHash = new HashMap<>();

        MyLocation.LocationResult lcaLocationResult = new MyLocation.LocationResult() {
            @Override
            public void gotLocation(Location location) {
                Toast.makeText(getApplicationContext(), "Latitude: " + location.getLatitude() + ", Longitude: " +location.getLongitude(), Toast.LENGTH_SHORT).show();
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        };
        myLocation = new MyLocation();
        myLocation.getLocation(this, lcaLocationResult);


        fragment_map = (MapFragment) getFragmentManager().findFragmentById( R.id.frag_map );
        fragment_map.getMapAsync( this );


    }

    @Override
    protected void onResume() {
        super.onResume();
            if ( Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return  ;
            }

            //Add marker dynamically.
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(ContactServer.ServerRequestUpdate){
                        if(MapIsReady) {

                            String jsonStr = contactServer.getServerRequest();
                            Log.d(TAG, jsonStr);

                            Gson gson = new Gson();
                            Type listType = new TypeToken<ArrayList<JsonObj>>() {
                            }.getType();
                            jsonArr = gson.fromJson(jsonStr, listType);

                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    for(JsonObj obj : jsonArr){
                                        Log.d(TAG, "GPSx: " + String.valueOf(obj.gps_x) + " GPSy: " + String.valueOf(obj.gps_y));

                                        LatLng provider = new LatLng(obj.gps_x, obj.gps_y);
                                        Marker marker = map.addMarker(new MarkerOptions().position(provider).title(obj.UserName).snippet
                                                (obj.UserId + "-" + obj.power_bank_spec).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory
                                                .HUE_YELLOW)));

                                        mapHash.put(marker.getId(), marker);


                                    }
                                }
                            });


                        }
                    }else{
                        contactServer.getHelp(latitude, longitude);

                    }
                }
            }, 100, 5000);



        //}


    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onMapReady( GoogleMap googleMap ) {
        //Toast.makeText( getApplicationContext(), "onMapReady", Toast.LENGTH_SHORT ).show();
        map = googleMap;
        MapIsReady = true;
        contactServer = new ContactServer(getApplicationContext());
        contactServer.getHelp(latitude, longitude);

        LatLng IamHere = new LatLng(22.114720, 113.325730 );
        map.addMarker( new MarkerOptions().position( IamHere ).title( "I am Here" ).snippet
                ( "and" + " snippet" ).icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory
                .HUE_BLUE ) ) );
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(IamHere, 15.0f));

        LatLng Provider = new LatLng(22.114720, 113.325840 );
        map.addMarker( new MarkerOptions().position( Provider ).title( "ABC" ).snippet
                ( "and" + " snippet" ).icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory
                .HUE_RED ) ) );
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(IamHere, 15.0f));


        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Click Marker");
                if (fragmentIsShowed == false) {

                    Bundle bundle = new Bundle();
                    bundle.putString("UserName", mapHash.get(marker.getId()).getTitle());
                    bundle.putDouble("Latitude", mapHash.get(marker.getId()).getPosition().latitude);
                    bundle.putDouble("Longitude", mapHash.get(marker.getId()).getPosition().longitude);
                    bundle.putString("UserId&PowerBankSpec", mapHash.get(marker.getId()).getSnippet().toString());
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
