package com.blogspot.cartonsbase.chargetogether;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.blogspot.cartonsbase.chargetogether.Network.JSON.JsonObj;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

public class Provider_page extends FragmentActivity implements OnMapReadyCallback{

    Gson gson;

    double latitude, longitude;
    String UserName;
    MapFragment fragment_map;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_page);

        fragment_map = (MapFragment) getFragmentManager().findFragmentById( R.id.frag_map_provider );
        fragment_map.getMapAsync( this );

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng IamHere = new LatLng(22.114720, 113.325830 );
        map.addMarker( new MarkerOptions().position( IamHere ).title( "I am Here" ).snippet
                ( "and" + " snippet" ).icon( BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory
                .HUE_BLUE) ) );

        LatLng Provider = new LatLng(22.114750, 113.325840 );
        map.addMarker( new MarkerOptions().position( Provider ).title( "ABC" ).snippet
                ( "and" + " snippet" ).icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory
                .HUE_RED ) ) );
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(IamHere, 20.0f));

        /*map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(22.114720, 113.325830), 15.0f));
*/
        // Polylines are useful for marking paths and routes on the map.
        map.addPolyline(new PolylineOptions().geodesic(true)
                .add(new LatLng(22.114720, 113.325830))  // Hawaii
                .add(new LatLng(22.114750, 113.325840))
        );
    }
}
