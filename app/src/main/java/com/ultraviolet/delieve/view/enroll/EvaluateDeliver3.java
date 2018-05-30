package com.ultraviolet.delieve.view.enroll;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.data.repository.AuthRepository;
import com.ultraviolet.delieve.data.repository.UserRepository;
import com.ultraviolet.delieve.view.base.BaseActivity;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EvaluateDeliver3 extends BaseActivity{

    @Inject
    UserRepository mUserRepository;
    private Bitmap selfie;
    private Bitmap idcard;

    @Inject

    @OnClick(R.id.btn_evaluatefinish)
    void onClick(){

        if (selfie != null && idcard != null) {

            ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
            selfie.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos1);
            MultipartBody.create(MediaType.parse("image/*"), bos1.toByteArray());

            ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
            idcard.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos2);
            MultipartBody.create(MediaType.parse("image/*"), bos2.toByteArray());

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_deliever3);
        ButterKnife.bind(this);

        selfie = getIntent().getParcelableExtra("selfie");
        idcard = getIntent().getParcelableExtra("id");

        getDiComponent().inject(this);

    }
}
