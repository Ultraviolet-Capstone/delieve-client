package com.ultraviolet.delieve.view.deliever;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.model.DeliveryMatchingForDeliever;
import com.ultraviolet.delieve.util.ImageLoadHelper;

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

    /*
    @BindView(R.id.matched_stuff_name)
    TextView mMatchedStuffName;

    @BindView(R.id.matched_stuff_weight)
    TextView mMatchedStuffWeight;
*/
    @BindView(R.id.button)
    Button button;

    @OnClick(R.id.button)
    public void onClick(){

    }

    DeliveryMatchingForDeliever mDeliveryMatchingForDeliever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliever_matched);
        ButterKnife.bind(this);
        mDeliveryMatchingForDeliever = (DeliveryMatchingForDeliever)getIntent().getSerializableExtra("Matching");

        setupUi();
    }

    private void setupUi() {
        mMatchedUsername.setText(mDeliveryMatchingForDeliever.senderName);
        mMatchedStartAddress.setText(mDeliveryMatchingForDeliever.beginAddress);
        mMatchedFinishAddress.setText(mDeliveryMatchingForDeliever.finishAddress);
        /*
        mMatchedStuffName.setText(mDeliveryMatchingForDeliever.stuffName);
        mMatchedStuffWeight.setText(mDeliveryMatchingForDeliever.stuffWeight + " Kg");
        */

        ImageLoadHelper.loadMapImage(mMatchedMapImageView, mDeliveryMatchingForDeliever.beginLatitude,
                mDeliveryMatchingForDeliever.beginLongitude,
                mDeliveryMatchingForDeliever.finishLatitude,
                mDeliveryMatchingForDeliever.finishLongitude,
                getString(R.string.googlemap_api_key));
        ImageLoadHelper.loadProfileImage(mMatchedProfileImageView,
                mDeliveryMatchingForDeliever.senderSelfiURL);
    }

}
