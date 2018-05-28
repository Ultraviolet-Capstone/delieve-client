package com.ultraviolet.delieve.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.ScanQrcode;


public class DeliverFragment extends Fragment {

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_deliver, container, false);
        //return inflater.inflate(R.layout.fragment_deliver, container, false);
        Button generateQRbtn=(Button)view.findViewById(R.id.btn_generate_qr);
        Button scanQRbtn=(Button)view.findViewById(R.id.btn_scan_qr);
        generateQRbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GenerateQrcode.class);
                startActivity(intent);
            }
        });
        scanQRbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(), ScanQrcode.class);
                startActivity(intent);
            }
        });
        return view;
    }
}