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
import com.ultraviolet.delieve.data.dto.LoginDto;
import com.ultraviolet.delieve.data.repository.AuthRepository;
import com.ultraviolet.delieve.data.repository.UserRepository;
import com.ultraviolet.delieve.view.base.BaseActivity;
import com.ultraviolet.delieve.view.main.MainActivity;

import javax.inject.Inject;

import okhttp3.MultipartBody;


public class KakaoSignupActivity extends BaseActivity {

    @Inject
    UserRepository mUserRepository;

    @Inject
    AuthRepository mAuthRepository;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDiComponent().inject(this);
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


                mAuthRepository.login(kakaoID)//kakaoID is token
                        .subscribe(res->{
                            LoginDto loginDto = res.body();
                            /*
                            if (res.code() == 200){
                                redirectMainActivity(url, kakaoNickname); // 로그인 성공시 MainActivity
                            }
                            else if(res.code() == 404){
                                redirectSignUpActivity(url, email);
                            }
                            */
                            if (res.code() == 200){
                                Log.d("session", "KakaoSignupActivity.requestMe.onSuccess.code200");
                                Log.d("session", "KakaoSignupActivity.requestMe.onSuccess.login.subscribe");
                                mUserRepository.userSignIn(loginDto);
                                getSharedPreferences("auto", Activity.MODE_PRIVATE).edit()
                                        .putString("accessToken", res.body().accessToken)
                                        .commit();
                                redirectMainActivity(url, kakaoNickname, kakaoID); // 로그인 성공시 MainActivity
                            }
                            else if(res.code() == 404){
                                Log.d("session", "KakaoSignupActivity.requestMe.onSuccess.code404");
                                redirectSignUpActivity(url, email, kakaoID, kakaoNickname);
                            }
                        }, throwable -> {
                            Log.d("delieve", throwable.getMessage());
                            Log.d("session", "KakaoSignupActivity.requestMe.onSuccess.login.throwable");
                            redirectSignUpActivity(url, email, kakaoID, kakaoNickname);
                        });

            }
        });
    }
    private void redirectSignUpActivity(String url, String email, String token, String nickname) {
        Intent intent = new Intent(this, SignupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra("url", url);
        intent.putExtra("email", email);
        intent.putExtra("token", token);
        intent.putExtra("nickname", nickname);
        startActivity(intent);
        finish();
    }
    private void redirectMainActivity(String url, String nickname, String kakaoID) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("nickname", nickname);
        intent.putExtra("kakaoId", kakaoID);
        startActivity(intent);
        finish();
    }
    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }


}
