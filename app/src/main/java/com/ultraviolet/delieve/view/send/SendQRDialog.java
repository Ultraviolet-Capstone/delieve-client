package com.ultraviolet.delieve.view.send;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.ultraviolet.delieve.R;

public class SendQRDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View  v = inflater.inflate(R.layout.dialog_qrcode,null);
        //.setView(v).
               // setPositiveButton();

        return builder.create();

    }

}
