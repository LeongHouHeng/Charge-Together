package com.blogspot.cartonsbase.chargetogether;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;

public class SpinningProgressDialog extends DialogFragment {
    private ProgressDialog progressDialog;

    public static SpinningProgressDialog newInstance(int title, int message) {
        SpinningProgressDialog fragment = new SpinningProgressDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        args.putInt("message", message);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle safedInstanceState) {
        int title = getArguments().getInt("title");
        int message = getArguments().getInt("message");

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(title);
        progressDialog.setMessage(getResources().getText(message));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        return progressDialog;
    }
}