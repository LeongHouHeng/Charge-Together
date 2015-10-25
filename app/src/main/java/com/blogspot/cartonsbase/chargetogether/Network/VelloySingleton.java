package com.blogspot.cartonsbase.chargetogether.Network;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by LeongHouHeng on 26/10/15.
 */
public class VelloySingleton extends Application{
    private static VelloySingleton instance;
    private RequestQueue queue;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static synchronized VelloySingleton getInstance(){
        return instance;
    }

    public RequestQueue getRequestQueue(){
        if ( queue == null ){
            queue = Volley.newRequestQueue( getApplicationContext() );

        }
        return queue;
    }

    public <T>void addToRequestQueue(Request<T> req ){
        getRequestQueue().add( req );
    }
}
