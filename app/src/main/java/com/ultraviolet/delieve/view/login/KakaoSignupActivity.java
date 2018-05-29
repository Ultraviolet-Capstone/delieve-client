package com.ultraviolet.delieve.view.login;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;

import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;

import com.kakao.util.helper.log.Logger;
import com.ultraviolet.delieve.view.main.MainActivity;



public class KakaoSignupActivity extends Activity {

    boolean isSignup=true;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestMe();
    }
    /**
     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    protected void requestMe() { //유저의 정보를 받아오는 함수
        UserManagement.getInstance().requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                redirectLoginActivity();
            }
            @Override
            public void onNotSignedUp() {} // 카카오톡 회원이 아닐 시 showSignup(); 호출해야함

            @Override
            public void onSuccess(UserProfile userProfile) {  //성공 시 userProfile 형태로 반환
                String kakaoID = String.valueOf(userProfile.getId()); // userProfile에서 ID값을 가져옴
                String kakaoNickname = userProfile.getNickname();     // Nickname 값을 가져옴
                String url = String.valueOf(userProfile.getProfileImagePath());
                String email=String.valueOf(userProfile.getEmail());

                Logger.d("UserProfile : " + userProfile);
                Log.d("kakao", "==========================");
                Log.d("kakao", ""+userProfile);
                Log.d("kakao", kakaoID);
                Log.d("kakao", kakaoNickname);
                Log.d("kakao", url);
                Log.d("kakao", email);
                Log.d("kakao", "==========================");


                // 우선 서버를 login()
                //  ip/auth/user/token?76~

                if (isSignup==false) {
                    redirectSignUpActivity(url, email);

                } else {
                    redirectMainActivity(url, kakaoNickname); // 로그인 성공시 MainActivity로
                }


                // 없으 => sig
                // 있어1 ->
            }
        });
    }
    private void redirectSignUpActivity(String url, String email) {
        Intent intent = new Intent(KakaoSignupActivity.this, SignupActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("email", email);
        startActivity(intent);
        finish();
    }
    private void redirectMainActivity(String url, String nickname) {
        Intent intent = new Intent(KakaoSignupActivity.this, MainActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("nickname", nickname);
        startActivity(intent);
        finish();
    }
    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }


}
