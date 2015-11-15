package com.blogspot.cartonsbase.chargetogether;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by LeongHouHeng on 28/10/15.
 */
public class InfoWindowFragment extends Fragment{

    private static final String TAG = "InfoWindowFragment";

    double latitude, longitude;
    String UserName;
    String UserId;
    String power_bank_spec;
    Button detailsBtn;

    View view;

    TextView tv_username;

    Provider_page pp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.fragment_infowindow, container, false );

        detailsBtn = (Button) view.findViewById(R.id.detailsBtn);
        pp = new Provider_page();

        Bundle bundle = getArguments();
        latitude = bundle.getDouble( "Latitude" );
        longitude = bundle.getDouble( "Longitude" );
        UserName = bundle.getString("UserName");
        String str = bundle.getString("UserId&PowerBankSpec");

        String array[] = str.split("-");
        Log.e(TAG, array[0] + " " + array[1]);
        UserId = array[0];
        power_bank_spec = array[1];

        //Log.e(TAG, str);
        tv_username = (TextView)view.findViewById(R.id.UserName);
        tv_username.setText( UserName );


        detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Click fragment");

                Bundle bundle = getArguments();
                bundle.putDouble("Latitude", latitude);
                bundle.putDouble("Longitude", longitude);
                bundle.putString("UserName", UserName);
                bundle.putString("UserId", UserId);
                bundle.putString("power_bank_spec", power_bank_spec);

                Intent intent = new Intent();
                intent.setClass(getActivity(), Provider_page.class);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);



    }

}
