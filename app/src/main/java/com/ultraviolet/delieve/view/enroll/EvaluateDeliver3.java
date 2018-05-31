package com.ultraviolet.delieve.view.enroll;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.data.repository.AuthRepository;
import com.ultraviolet.delieve.data.repository.UserRepository;
import com.ultraviolet.delieve.data.service.EnrollService;
import com.ultraviolet.delieve.view.base.BaseActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

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
    EnrollService mEnrollService;

    @OnClick(R.id.btn_evaluatefinish)
    void onClick(){

        Bitmap bmp1 = selfie;
        Bitmap bmp2 = idcard;

        if (selfie != null && idcard != null) {

            ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
            selfie.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos1);
            MultipartBody.create(MediaType.parse("image/*"), bos1.toByteArray());

            ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
            idcard.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos2);
            MultipartBody.create(MediaType.parse("image/*"), bos2.toByteArray());

            bmp1 = selfie;
            bmp2 = idcard;
        }
        else {
            int w = 300, h = 300;

            Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
            bmp1 = Bitmap.createBitmap(w, h, conf); // this creates a MUTABLE bitmap

            Bitmap.Config conf2 = Bitmap.Config.ARGB_8888; // see other conf types
            bmp2 = Bitmap.createBitmap(w, h, conf2); // this creates a MUTABLE bitmap
        }
            File filesDir = getFilesDir();
            File img1 = new File(filesDir, "img1.jpg");
            File img2 = new File(filesDir, "img2.jpg");

            OutputStream os;
            try {
                os = new FileOutputStream(img1);
                bmp1.compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.flush();
                os.close();
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
            }
            try {
                os = new FileOutputStream(img2);
                bmp2.compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.flush();
                os.close();
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
            }

            /*
            ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
            bmp1.compress(Bitmap.CompressFormat.JPEG, 0 , bos1);*/
            RequestBody body1 = MultipartBody.create(MediaType.parse("image"), img1);

            /*
            ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
            bmp2.compress(Bitmap.CompressFormat.JPEG, 0, bos2);*/
            RequestBody body2 = MultipartBody.create(MediaType.parse("image"), img2);

            RequestBody body3 = MultipartBody.create(MediaType.parse("text/plain"), "test");
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            mEnrollService.requestEnroll(
                    MultipartBody.Part.createFormData("selfi", img1.getName(), body1),
                    MultipartBody.Part.createFormData("idcard", img2.getName(), body2),
                    MultipartBody.Part.createFormData("userId", "" + mUserRepository.getUserId()))
                    .subscribe(res->{
                        Log.d("credt", res.code() + "");
                    }, throwable -> {
                        throwable.printStackTrace();
                    });

            setResult(1);
            finish();
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
