package com.ultraviolet.delieve.view.send;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.data.repository.DeliveryRepository;
import com.ultraviolet.delieve.data.repository.QRApiRepository;
import com.ultraviolet.delieve.model.DeliveryMatching;
import com.ultraviolet.delieve.model.DeliveryMatchingForDeliever;
import com.ultraviolet.delieve.util.QRGenerator;
import com.ultraviolet.delieve.view.base.BaseActivity;
import com.ultraviolet.delieve.view.deliever.DelieverMatchedDialogActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SendMatchedActivity extends BaseActivity {

    DeliveryMatching mDeliveryMatching;

    private GoogleMap mGoogleMap;
    private Marker mMarker;
    private SupportMapFragment mSupportMapFragment;

    @Inject
    QRApiRepository mQrApiRepository;

    @Inject
    DeliveryRepository mDeliveryRepository;

    @BindView(R.id.map_frame)
    FrameLayout mMapFrameLayout;


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

        EventBus.getDefault().register(this);

        initMap();
        initTracking();

    }

    Subscription subscription;
    private void initTracking() {
        subscription = Observable.interval(2000, 5000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    public void call(Long aLong) {
                        mDeliveryMatching
                    }
                });

    }

    private void initMap() {
    mSupportMapFragment = SupportMapFragment.newInstance();
    mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mGoogleMap = googleMap;
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(37.284337, 127.044246), 15
                    ));
                    getSupportFragmentManager().beginTransaction().replace(R.id.map_frame, mSupportMapFragment).commit();
                }
            });
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
