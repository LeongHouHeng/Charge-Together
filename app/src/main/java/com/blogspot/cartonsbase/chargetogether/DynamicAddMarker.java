package com.blogspot.cartonsbase.chargetogether;

import android.os.AsyncTask;

import com.blogspot.cartonsbase.chargetogether.Network.JSON.JsonObj;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by classroom on 15/11/6.
 */
public class DynamicAddMarker extends AsyncTask<String, Void, String> {

    public GoogleMap map;

    public DynamicAddMarker(GoogleMap map){
        this.map = map;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<JsonObj>>() {}.getType();
        ArrayList<JsonObj> jsonArr = gson.fromJson(params.toString(), listType);
        for(JsonObj obj : jsonArr){
            LatLng provider = new LatLng(obj.gps_x, obj.gps_y);
            map.addMarker( new MarkerOptions().position( provider ).title( "Provider" ).snippet
                    ( "and" + " snippet" ).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory
                    .HUE_RED)) );

        }

        return null;
    }
}
