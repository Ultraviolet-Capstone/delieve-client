package com.ultraviolet.delieve.view.send;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.data.repository.DeliveryRepository;
import com.ultraviolet.delieve.data.repository.QRApiRepository;
import com.ultraviolet.delieve.model.DeliveryMatching;
import com.ultraviolet.delieve.util.QRGenerator;
import com.ultraviolet.delieve.view.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendMatchedActivity extends BaseActivity {

    DeliveryMatching mDeliveryMatching;

    @Inject
    QRApiRepository mQrApiRepository;

    @Inject
    DeliveryRepository mDeliveryRepository;

    @BindView(R.id.matched_vf)
    ViewFlipper mViewFlipper;

    @OnClick(R.id.send_matched_button)
    void onClick(){
        mQrApiRepository.getQRHash(mDeliveryMatching.matchingId,
            "READY")
            .subscribe(res-> {
                Log.d("credt", res.code() + "");
                showQRDialog(res.body().hashValue);
            }, throwable ->{
                Log.d("credt",  "wtf");
                throwable.printStackTrace();
            });
    }

    private void showQRDialog(String hash) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_qrcode,null);
        Bitmap qrCodeBmp = QRGenerator.generateQRCode(hash);
        ImageView qrImageView = v.findViewById(R.id.dialog_qr);
        qrImageView.setImageBitmap(qrCodeBmp);
        b.setView(v);
        b.setPositiveButton("양도 확인", (dialog, which)-> {
            mDeliveryRepository
                    .getDeliveryMatchingInfoByMatchingIdForSender(mDeliveryMatching.matchingId)
                    .subscribe(res -> {
                        if (res.body().matchingStatus.equals("READY")){
                            Log.d("credt", "QR code is not transfered.");
                        }
                    }, throwable -> {
                        throwable.printStackTrace();
                    });
        });
        b.setNegativeButton("취 소", (dialog, which)->{
            dialog.cancel();
        });
        b.show().setCanceledOnTouchOutside(false);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_matched);
        getDiComponent().inject(this);
        ButterKnife.bind(this);
        mDeliveryMatching = (DeliveryMatching) getIntent()
                .getSerializableExtra("Matching");

        switch (mDeliveryMatching.matchingStatus){
            case "READY":
                setupReadyMode();
                break;
            case "PROGRESS":
                setupProgressMode();
                break;
            case "FINISH":
                setupFinishMode();
                break;
        }
    }

    private void setupFinishMode() {

    }

    private void setupProgressMode() {
        mViewFlipper.setDisplayedChild(1);
    }

    private void setupReadyMode() {
        mViewFlipper.setDisplayedChild(0);
    }
}
