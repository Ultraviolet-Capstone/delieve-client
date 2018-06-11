package com.ultraviolet.delieve.view.deliever;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.model.DeliveryMatching;
import com.ultraviolet.delieve.model.DeliveryMatchingForDeliever;
import com.ultraviolet.delieve.util.ImageLoadHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DelieverMatchedActivity extends AppCompatActivity {
    private IntentIntegrator qrScan;


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

    @BindView(R.id.matched_stuff_size)
    TextView mMatchedStuffSize;

    @BindView(R.id.matched_stuff_weight)
    TextView mMatchedStuffWeight;

    @BindView(R.id.matched_stuff_name)
    TextView mMatchedStuffName;

    @BindView(R.id.deliever_matched_distance)
    TextView mMatchedDistance;

    @BindView(R.id.button_transfer_from_sender)
    Button button;

    @OnClick(R.id.button_transfer_from_sender)
    public void onClick(){
        qrScan = new IntentIntegrator(this);
        qrScan.setPrompt("Scanning...");
        //qrScan.setOrientationLocked(false);
        qrScan.initiateScan();

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
        mMatchedStuffWeight.setText(mDeliveryMatching.stuffWeight + " Kg");
        mMatchedStuffName.setText(mDeliveryMatching.stuffName);
        mMatchedStuffSize.setText(mDeliveryMatching.stuffSize);
        mMatchedDistance.setText(String.format("%.2f", mDeliveryMatching.distance) + "Km");


        /*
        mMatchedStuffName.setText(mDeliveryMatching.stuffName);
        mMatchedStuffWeight.setText(mDeliveryMatching.stuffWeight + " Kg");
        */

        ImageLoadHelper.loadMapImage(mMatchedMapImageView, mDeliveryMatching.beginLatitude,
                mDeliveryMatching.beginLongitude,
                mDeliveryMatching.finishLatitude,
                mDeliveryMatching.finishLongitude,
                getString(R.string.googlemap_api_key));
        ImageLoadHelper.loadProfileImage(mMatchedProfileImageView,
                mDeliveryMatching.senderSelfiURL);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //qrcode 가 없으면
            if (result.getContents() == null) {
                Toast.makeText(this, "취소!", Toast.LENGTH_SHORT).show();
            } else {
                //qrcode 결과가 있으면
                Toast.makeText(this, "스캔완료!", Toast.LENGTH_SHORT).show();
                try {
                    //data를 json으로 변환
                    JSONObject obj = new JSONObject(result.getContents());
                    Log.d("scanQR", obj.getString("name"));
                    Log.d("scanQR", obj.getString("address"));
                    //textViewName.setText(obj.getString("name"));
                    //textViewAddress.setText(obj.getString("address"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    //textViewResult.setText(result.getContents());

                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
