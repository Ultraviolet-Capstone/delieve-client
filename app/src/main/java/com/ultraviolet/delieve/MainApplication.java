package com.ultraviolet.delieve;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;

import com.kakao.auth.KakaoSDK;
import com.ultraviolet.delieve.dagger.DaggerDiComponent;
import com.ultraviolet.delieve.dagger.DiComponent;
import com.ultraviolet.delieve.dagger.module.ApplicationModule;
import com.ultraviolet.delieve.dagger.module.NetworkModule;
import com.ultraviolet.delieve.view.login.KakaoSDKAdapter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.kakao.util.helper.Utility.getPackageInfo;


public class MainApplication extends Application {

    private DiComponent diComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        // Kakao Sdk 초기화
        KakaoSDK.init(new KakaoSDKAdapter(this));
        Log.d("guri",getKeyHash(getApplicationContext()));



    }

    private void init() {
        diComponent = DaggerDiComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule(getString(R.string.BASE_URL)))
                .build();


    }

    public static String getKeyHash(final Context context) {
        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES);
        if (packageInfo == null)
            return null;

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException e) {
                Log.w("guri", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
        return null;
    }

    public DiComponent getDiComponent() {
        return diComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        }


}
