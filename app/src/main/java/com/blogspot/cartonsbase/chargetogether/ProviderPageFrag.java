package com.blogspot.cartonsbase.chargetogether;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;

/**
 * Created by classroom on 15/11/4.
 */
public class ProviderPageFrag extends Fragment{

    private static final String TAG = "ProviderPageFrag Debugger";
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.fragment_provider, container, false );
        Button btn = (Button)view.findViewById(R.id.btn_request);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int interval = 3000;
                final SpinningProgressDialog dialogFragment = SpinningProgressDialog
                        .newInstance(R.string.app_name, R.string.dialog_message);
                dialogFragment.show(getFragmentManager(), "dialog_fragment");

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogFragment.dismiss();
                        OkDialog OKdialogFragment = OkDialog
                                .newInstance(R.string.app_name, R.string.dialog_message_finish);
                        OKdialogFragment.show(getFragmentManager(), "dialog_fragment");

                    }
                }, interval);


            }
        });
        return view;
    }
}
