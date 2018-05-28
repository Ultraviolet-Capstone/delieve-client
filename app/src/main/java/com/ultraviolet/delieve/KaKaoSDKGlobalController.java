package com.ultraviolet.delieve;

import android.app.Application;
import com.kakao.auth.KakaoSDK;
import com.ultraviolet.delieve.adapter.KakaoSDKAdapter;


public class KaKaoSDKGlobalController extends Application{
    private static KaKaoSDKGlobalController instance;

    public static KaKaoSDKGlobalController getGlobalApplicationContext() {
        if (instance == null) {
            throw new IllegalStateException("This Application does not inherit com.kakao.GlobalApplication");

        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // Kakao Sdk 초기화

        KakaoSDK.init(new KakaoSDKAdapter());
    }
    @Override

    public void onTerminate() {

        super.onTerminate();

        instance = null;
    }





}
