package com.ultraviolet.delieve.view.deliever;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.model.DeliveryMatching;
import com.ultraviolet.delieve.util.ImageLoadHelper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DelieverMatchedActivity extends AppCompatActivity {

    @BindView(R.id.matched_map_image_view)
    ImageView mMatchedMapImageView;

    @BindView(R.id.matched_profile_image)
    ImageView mMatchedProfileImageView;

    @BindView(R.id.matched_username)
    TextView mMatchedUsername;

    @BindView(R.id.matched_startaddress)
    TextView mMatchedStartAddress;

    @BindView(R.id.matched_finishaddress)
    TextView mMatchedFinishAddress;

    @BindView(R.id.button)
    Button button;

    @OnClick(R.id.button)
    public void onClick(){

    }

    DeliveryMatching mDeliveryMatching;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliever_matched);
        ButterKnife.bind(this);
        mDeliveryMatching = (DeliveryMatching)getIntent().getSerializableExtra("Matching");

        setupUi();
    }

    private void setupUi() {
        mMatchedUsername.setText(mDeliveryMatching.senderName);
        mMatchedStartAddress.setText(mDeliveryMatching.beginAddress);
        mMatchedFinishAddress.setText(mDeliveryMatching.finishAddress);
        ImageLoadHelper.loadMapImage(mMatchedMapImageView, mDeliveryMatching.beginLatitude,
                mDeliveryMatching.beginLongitude,
                mDeliveryMatching.finishLatitude,
                mDeliveryMatching.finishLongitude,
                getString(R.string.googlemap_api_key));
        ImageLoadHelper.loadProfileImage(mMatchedProfileImageView,
                mDeliveryMatching.senderSelfiURL);
    }

}
