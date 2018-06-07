package com.ultraviolet.delieve.view.send;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.model.DeliveryMatchingForSender;
import com.ultraviolet.delieve.view.base.BaseActivity;

public class SendMatchedActivity extends BaseActivity {

    DeliveryMatchingForSender mDeliveryMatchingForSender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_matched);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        mDeliveryMatchingForSender = (DeliveryMatchingForSender) getIntent()
                .getSerializableExtra("Matching");
        
    }
}
