package com.ultraviolet.delieve;

import android.app.Application;

import com.kakao.auth.KakaoSDK;
import com.ultraviolet.delieve.dagger.DaggerDiComponent;
import com.ultraviolet.delieve.dagger.DiComponent;
import com.ultraviolet.delieve.dagger.module.ApplicationModule;
import com.ultraviolet.delieve.dagger.module.NetworkModule;
import com.ultraviolet.delieve.view.login.KakaoSDKAdapter;


public class MainApplication extends Application {

    private DiComponent diComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        // Kakao Sdk 초기화
        KakaoSDK.init(new KakaoSDKAdapter(this));
    }

    private void init() {
        diComponent = DaggerDiComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule(getString(R.string.BASE_URL)))
                .build();
    }

    public DiComponent getDiComponent() {
        return diComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        }


}
