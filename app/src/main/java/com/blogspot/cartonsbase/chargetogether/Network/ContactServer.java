package com.blogspot.cartonsbase.chargetogether.Network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ContactServer {
    private static final String TAG = "Contact Server Debugger";

    String url_getHelp = "http://home.puiching.edu.mo/ChargerTogether/NeedHelp.php";
    String url_sendHelp = "http://home.puiching.edu.mo/ChargerTogether/Help.php";
    VelloySingleton velloySingleton;
    Context context;

    public ContactServer(Context context){
        velloySingleton = new VelloySingleton(context);
        this.context = context;
    }

    public void getHelp( final double GPSx, final double GPSy){

        StringRequest stringRequest = new StringRequest( Request.Method.POST, url_getHelp, new Response.Listener< String >() {

            @Override
            public void onResponse( String response ) {
                Log.d( TAG, response.toString() );

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
    }

}
