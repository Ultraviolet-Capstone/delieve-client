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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.data.repository.DeliveryRepository;
import com.ultraviolet.delieve.data.repository.GPSTrackingRepository;
import com.ultraviolet.delieve.data.repository.QRApiRepository;
import com.ultraviolet.delieve.model.DeliveryMatching;
import com.ultraviolet.delieve.model.DeliveryMatchingForDeliever;
import com.ultraviolet.delieve.util.QRGenerator;
import com.ultraviolet.delieve.view.base.BaseActivity;
import com.ultraviolet.delieve.view.deliever.DelieverMatchedDialogActivity;


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

    @Inject
    GPSTrackingRepository mGpsTrackingRepository;

    @BindView(R.id.send_matched_begin) TextView mBeginTextView;
    @BindView(R.id.send_matched_finish) TextView mFinishTextView;
    @BindView(R.id.send_matched_phone) TextView mPhoneNumberTextView;
    @BindView(R.id.send_matched_size) TextView mSizeTextView;
    @BindView(R.id.send_matched_weight) TextView mWeightTextView;
    @BindView(R.id.send_matched_stuff_name) TextView mNameTextView;
    @BindView(R.id.matched_vf) ViewFlipper mViewFlipper;
    @BindView(R.id.send_match_percentage) TextView mPercentage;
    @BindView(R.id.send_match_progressbar) ProgressBar mProgressBar;
    @BindView(R.id.send_matched_distance) TextView mDistance;


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
                        else if (res.body().matchingStatus.equals("PROGRESS")){
                            Log.d("credt","package is transfered.");
                            mDeliveryMatching.matchingStatus = "PROGRESS";
                            setupProgressMode();
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
        setupUi();
        initMap();
        initTracking();
    }

    private void setupUi() {
        mBeginTextView.setText(mDeliveryMatching.beginAddress);
        mFinishTextView.setText(mDeliveryMatching.finishAddress);
        mSizeTextView.setText(mDeliveryMatching.stuffSize);
        mWeightTextView.setText(mDeliveryMatching.stuffWeight + " Kg");
        mPhoneNumberTextView.setText(mDeliveryMatching.recieverPhone);
        mNameTextView.setText(mDeliveryMatching.stuffName);
        mDistance.setText(String.format("%.2f", mDeliveryMatching.distance) + " Km");
    }

    Subscription subscription;
    private void initTracking() {
        subscription = Observable.interval(1000, 3000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    public void call(Long aLong) {
                        mGpsTrackingRepository.getGPS(mDeliveryMatching.matchingId)
                                .subscribe(res->{
                                    if (res.code() != 200 || res.body() == null){
                                        Log.d("credt", "getting gps from server failed.");
                                        return;
                                    }
                                    Log.i("credt",
                                            "GPS tracking info : "
                                                    + res.body().latitude + ", "
                                                    + res.body().longitude);
                                    LatLng latLng = new LatLng(
                                            res.body().latitude,
                                            res.body().longitude);
                                    if (mMarker == null) {
                                        MarkerOptions a = new MarkerOptions().position(latLng);
                                        mMarker = mGoogleMap.addMarker(a);
                                    }
                                    else{
                                        mMarker.setPosition(latLng);
                                    }
                                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                    if (mDeliveryMatching.matchingStatus.equals("PROGRESS")){
                                        updateProgressBar(res.body().latitude, res.body().longitude);
                                    }
                                }, throwable -> {
                                    throwable.printStackTrace();
                                });

                    }
                });
    }

    private void updateProgressBar(double lat, double lng) {
        double totalDisrtance = mDeliveryMatching.distance;
        Log.d("credt", "total distance percent : " +  totalDisrtance);

        double remainedDistance;
        remainedDistance = distanceGPS(lat,lng,
                mDeliveryMatching.finishLatitude,
                mDeliveryMatching.finishLongitude,
        "K");

        Log.d("credt", "remained distance percent : " +  remainedDistance);

        int percent =(int)(remainedDistance / totalDisrtance * 100) ;
        Log.d("credt", "distance percent : " +  percent);
        if (percent > 100) percent = 100;
        mPercentage.setText(String.format("%d", percent) + "%");


        mProgressBar.setProgress((percent), true);

    }
    private static double distanceGPS(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::	This function converts decimal degrees to radians						 :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::	This function converts radians to decimal degrees						 :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    private void initMap() {
        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_frame);
    mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mGoogleMap = googleMap;
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(37.284337, 127.044246), 13
                    ));
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
