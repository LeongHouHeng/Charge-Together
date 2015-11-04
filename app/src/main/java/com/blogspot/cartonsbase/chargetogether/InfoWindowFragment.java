package com.blogspot.cartonsbase.chargetogether;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by LeongHouHeng on 28/10/15.
 */
public class InfoWindowFragment extends Fragment{

    private static final String TAG = "InfoWindowFragment Debugger";

    double latitude, longitude;
    String UserName;

    View view;

    TextView tv_username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.fragment_infowindow, container, false );
        Bundle bundle = getArguments();
        latitude = bundle.getDouble( "Latitude" );
        longitude = bundle.getDouble( "Longitude" );
        UserName = bundle.getString("UserName");

        tv_username = (TextView)view.findViewById( R.id.UserName );
        tv_username.setText( "UseName: " + UserName );

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    Log.d(TAG, "Click fragment");

                }

                return true;
            }
        });

        return view;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);



    }

}
