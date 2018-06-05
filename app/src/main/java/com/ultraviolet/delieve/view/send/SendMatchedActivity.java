package com.ultraviolet.delieve.view.send;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.model.DeliveryMatchingForSender;
import com.ultraviolet.delieve.util.QRGenerator;
import com.ultraviolet.delieve.view.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendMatchedActivity extends BaseActivity {

    DeliveryMatchingForSender mDeliveryMatchingForSender;

    @OnClick(R.id.send_matched_button)
    void onClick(){
        showQRDialog();
        DialogFragment fragment = new SendQRDialog();
        fragment.show(getFragmentManager(), null);
    }

    private void showQRDialog() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_qrcode,null);
        Bitmap qrCodeBmp = QRGenerator.generateQRCode("test");
        ImageView qrImageView = v.findViewById(R.id.dialog_qr);
        qrImageView.setImageBitmap(qrCodeBmp);
        b.setView(v);
        b.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_matched);
        ButterKnife.bind(this);
        mDeliveryMatchingForSender = (DeliveryMatchingForSender) getIntent()
                .getSerializableExtra("Matching");
        
    }


}
