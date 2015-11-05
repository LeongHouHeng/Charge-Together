package com.blogspot.cartonsbase.chargetogether.Network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.cartonsbase.chargetogether.Home;
import com.blogspot.cartonsbase.chargetogether.Network.JSON.JsonObj;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ContactServer{
    private static final String TAG = "Contact Server";

    public static boolean ServerRequestUpdate = false;

    String url_getHelp = "http://home.puiching.edu.mo/ChargerTogether/NeedHelp.php";
    String url_sendHelp = "http://home.puiching.edu.mo/ChargerTogether/Help.php";
    VelloySingleton velloySingleton;
    Home home;

    public String ServerRequest;

    Context context;

    public ContactServer(Context context){
        home = new Home();
        ServerRequest = new String();
        //ServerRequest = ";";
        velloySingleton = new VelloySingleton(context);
        this.context = context;
    }

    public ContactServer(){

    }

    public void getHelp( final double GPSx, final double GPSy){
        //String ServerRequest;
        StringRequest stringRequest = new StringRequest( Request.Method.POST, url_getHelp, new Response.Listener< String >() {


            @Override
            public void onResponse( String response ) {
                //static String ServerRequest;

                ServerRequest = response.toString();
                Log.d( TAG, /*response.toString()*/ServerRequest );
                setServerRequest(ServerRequest);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse( VolleyError error ) {
                VolleyLog.v( "Error: ", error.getMessage() );
            }
        } ){
            @Override
            protected Map< String, String > getParams() {
                Map<String, String> params = new HashMap<String, String>(  );
                params.put( "GPS_x", String.valueOf( GPSx ) );
                params.put( "GPS_y", String.valueOf( GPSy ) );
                return params;
            }


        };
        VelloySingleton.getInstance(context).addToRequestQueue( stringRequest );
        //return ServerRequest;

    }

    public void sendHelp(double GPSx, double GPSy, int UserType, String PowerBankSpec, double prefer_GPSx, double prefer_GPSy, String prefer_time, String bluetooth_mac_address){
       //TODO: Provider post info...
    }

    public void setServerRequest(String ServerRequest){
        Log.d(TAG, "1 set" + ServerRequest);
        this.ServerRequest = ServerRequest;
        Log.d(TAG, "2 set" + this.ServerRequest);
        ServerRequestUpdate = true;

    }

    public String getServerRequest(){
        ServerRequestUpdate = false;
        Log.e(TAG, "3 get" + ServerRequest);
        return ServerRequest;

    }



}
