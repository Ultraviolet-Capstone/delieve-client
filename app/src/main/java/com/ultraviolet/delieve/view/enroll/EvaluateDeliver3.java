package com.ultraviolet.delieve.view.enroll;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.data.repository.AuthRepository;
import com.ultraviolet.delieve.view.base.BaseActivity;

import javax.inject.Inject;

public class EvaluateDeliver3 extends BaseActivity{

    @Inject
    AuthRepository mAuthRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_deliever3);

        Bitmap selfie=(Bitmap)getIntent().getParcelableExtra("selfie");
        Bitmap idcard=(Bitmap)getIntent().getParcelableExtra("id");




        getDiComponent().inject(this);

    }
}
