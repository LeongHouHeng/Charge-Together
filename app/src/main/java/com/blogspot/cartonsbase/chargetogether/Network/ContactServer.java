package com.blogspot.cartonsbase.chargetogether.Network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ContactServer {
    private static final String TAG = "Contact Server Debugger";

    String url_getHelp = "http://home.puiching.edu.mo/ChargerTogether/NeedHelp.php";
    String url_sendHelp = "http://home.puiching.edu.mo/ChargerTogether/Help.php";
    VelloySingleton velloySingleton;

    public ContactServer(){
        velloySingleton = new VelloySingleton();
    }

    public void getHelp( final double GPSx, final double GPSy){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.POST, url_getHelp,
                null, new Response.Listener< JSONObject >() {

            @Override
            public void onResponse( JSONObject response ) {
                Log.d( TAG, response.toString() );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse( VolleyError error ) {
                VolleyLog.d( TAG, "Error: " + error.getMessage() );
            }
        } ){

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("GPSx", String.valueOf( GPSx ));
                params.put("GPSy", String.valueOf( GPSy ));

                return params;
            }
        };

        //// TODO: 26/10/15 NULL POINTER HERE , PROBLEMS MAYBE IN VelloySingleton.java
        VelloySingleton.getInstance().addToRequestQueue( jsonObjectRequest );
    }

}
