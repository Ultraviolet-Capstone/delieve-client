package com.ultraviolet.delieve.view.deliever;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.data.dto.DelieverAcceptDto;
import com.ultraviolet.delieve.data.repository.DeliveryRepository;
import com.ultraviolet.delieve.data.repository.UserRepository;
import com.ultraviolet.delieve.model.DeliveryMatchingForDeliever;
import com.ultraviolet.delieve.util.ImageLoadHelper;
import com.ultraviolet.delieve.view.base.BaseActivity;

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

    DeliveryMatchingForDeliever mDeliveryMatchingForDeliever;

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

    @BindView(R.id.matched_stuff_name)
    TextView mMatchedStuffName;

    @BindView(R.id.matched_stuff_weight)
    TextView mMatchedStuffWeight;

    @BindView(R.id.matched_stuff_size)
    TextView mMatchedStuffSize;


    @OnClick(R.id.matched_accept)
    public void onAcceptClick(){
        mDeliveryRepository.delieverAccept(new DelieverAcceptDto(
                mUserRepository.getUserId(),
                mDeliveryMatchingForDeliever.requestId,
                (new Date()).toString()
        )).subscribe(res -> {
            if(res.code() == 200 && res.body().delivererId == mUserRepository.getUserId())
                Log.d("credt", "successfully accepted request");
            setResult(RESULT_OK);
            finish();
        }, throwable -> {
        });
    }
    @OnClick(R.id.matched_decline)
    public void onDeclineClicked(){
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliever_matched_dialog);
        mDeliveryMatchingForDeliever = (DeliveryMatchingForDeliever)getIntent().getSerializableExtra("Matching");
        getDiComponent().inject(this);
        ButterKnife.bind(this);
        setupUi();
    }

    void setupUi(){

        mMatchedStuffName.setText(mDeliveryMatchingForDeliever.stuffName);
        mMatchedUsername.setText(mDeliveryMatchingForDeliever.senderName);
        mMatchedStartAddress.setText(mDeliveryMatchingForDeliever.beginAddress);
        mMatchedFinishAddress.setText(mDeliveryMatchingForDeliever.finishAddress);
        mMatchedStuffWeight.setText(mDeliveryMatchingForDeliever.stuffWeight + " Kg");
        mMatchedStuffSize.setText(mDeliveryMatchingForDeliever.stuffSize);
        ImageLoadHelper.loadMapImage(mMapImageView, mDeliveryMatchingForDeliever.beginLatitude,
                mDeliveryMatchingForDeliever.beginLongitude,
                mDeliveryMatchingForDeliever.finishLatitude,
                mDeliveryMatchingForDeliever.finishLongitude,
                getString(R.string.googlemap_api_key));
        ImageLoadHelper.loadProfileImage(mMatchedProfileImageView,
                mDeliveryMatchingForDeliever.senderSelfiURL);

    }

}
