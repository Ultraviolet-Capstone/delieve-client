package com.ultraviolet.delieve.view.deliever;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.data.dto.DelieverAcceptDto;
import com.ultraviolet.delieve.data.repository.DeliveryRepository;
import com.ultraviolet.delieve.data.repository.UserRepository;
import com.ultraviolet.delieve.model.DeliveryMatching;
import com.ultraviolet.delieve.util.StaticMapHelper;
import com.ultraviolet.delieve.view.base.BaseActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DelieverMatchedDialogActivity extends BaseActivity {

    @Inject
    UserRepository mUserRepository;
    @Inject
    DeliveryRepository mDeliveryRepository;

    DeliveryMatching mDeliveryMatching;

    @BindView(R.id.matched_map_image_view)
    ImageView mMapImageView;

    @BindView(R.id.matched_profile_image)
    ImageView mMatchedProfileImageView;

    @BindView(R.id.matched_username)
    TextView mMatchedUsername;

    @BindView(R.id.matched_startaddress)
    TextView mMatchedStartAddress;

    @BindView(R.id.matched_finishaddress)
    TextView mMatchedFinishAddress;

    @OnClick(R.id.matched_accept)
    public void onAcceptClick(){
        mDeliveryRepository.delieverAccept(new DelieverAcceptDto(
                mUserRepository.getUserId(),
                mDeliveryMatching.requestId,
                (new Date()).toString()
        )).subscribe(res -> {
            if(res.code() == 200 && res.body().delivererId == mUserRepository.getUserId())
                Log.d("credt", "successfully accepted request");
            Intent intent = new Intent(getApplicationContext(), DelieverMatchedActivity.class);
            startActivity(intent);
        }, throwable -> {
        });
    }
    @OnClick(R.id.matched_decline)
    public void onDeclineClicked(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliever_matched_dialog);
        mDeliveryMatching = (DeliveryMatching)getIntent().getSerializableExtra("Matching");
        getDiComponent().inject(this);
        ButterKnife.bind(this);
        setupUi();
    }

    void setupUi(){
        loadProfileImage();
        loadMapImage();
        mMatchedUsername.setText(mDeliveryMatching.senderName);
        mMatchedStartAddress.setText(mDeliveryMatching.beginAddress);
        mMatchedFinishAddress.setText(mDeliveryMatching.finishAddress);

    }

    public void loadProfileImage () {
        Log.d("credt", "image url : " + mDeliveryMatching.senderSelfiURL);
        //start a background thread for networking
        new Thread(() -> {
            try {
                //download the drawable
                final Drawable drawable =
                        Drawable.createFromStream(
                                (InputStream) new URL(mDeliveryMatching.senderSelfiURL).getContent(), "kakao_profile_src");
                //edit the view in the UI thread
                mMatchedProfileImageView.post(() -> mMatchedProfileImageView.setImageDrawable(drawable));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void loadMapImage(){
        String mapURL = StaticMapHelper.buildURL(mDeliveryMatching.beginLatitude,
                mDeliveryMatching.beginLongitude,
                getString(R.string.googlemap_api_key));
        new Thread(() -> {
            try {
                //download the drawable
                final Drawable drawable =
                        Drawable.createFromStream(
                                (InputStream) new URL(mapURL).getContent(), "map_image_src");
                //edit the view in the UI thread
                mMapImageView.post(() -> mMapImageView.setImageDrawable(drawable));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
