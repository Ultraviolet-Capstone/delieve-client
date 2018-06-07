package com.ultraviolet.delieve.view.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
import com.ultraviolet.delieve.R;

import java.security.MessageDigest;
public class LoginActivity extends AppCompatActivity {
    private Context mContext;

    private LoginButton btn_kakao_login;
    SessionCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = getApplicationContext();
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        callback=new SessionCallback();
        Log.d("session", "2");
        Session.getCurrentSession().addCallback(callback);

        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        String AUTO_ACCESS_TOKEN = auto.getString("accessToken", null);

        if (!Session.getCurrentSession().isClosed()) {
            // session 있음
            Log.d("account-set", "session o");
            if (AUTO_ACCESS_TOKEN == null) {
                // if no logged in data
                // then  remove the session
                Session.getCurrentSession().close();
                Log.d("account-set", "session o but no accessToken. So, remove session");
            }
            else {
                // if there is logged in data
                // activate auto login
                Log.d("account-set",
                        "session o and accessToken exist. Activate Auto Login");

            }
            Session.getCurrentSession().checkAndImplicitOpen();
        }
        else {
            // there is no session
            // then do nothing. Just wait for users event triggering.
            Log.d("account-set", "session x");

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }
    protected void redirectSignupActivity() {
        final Intent intent = new Intent(this, KakaoSignupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        Log.d("session", "LoginActivity.redirectSignupActivity...");
        finish();
    }

    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            redirectSignupActivity();  // 세션 연결성공 시 redirectSignupActivity() 호출
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Logger.e(exception);
            }
            Log.d("session", "fail 연결");
            setContentView(R.layout.activity_login); // 세션 연결이 실패했을때
        }
    }


    //


        // 사용자 정보 요청

}
