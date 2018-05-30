package com.ultraviolet.delieve.view.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MenuItem;

import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.data.dto.DeliveryMatchingDto;
import com.ultraviolet.delieve.data.repository.AuthRepository;
import com.ultraviolet.delieve.data.repository.DeliveryRepository;
import com.ultraviolet.delieve.data.repository.EnrollRepository;
import com.ultraviolet.delieve.data.repository.UserRepository;
import com.ultraviolet.delieve.data.service.EnrollService;
import com.ultraviolet.delieve.view.base.BaseActivity;
import com.ultraviolet.delieve.view.deliever.DelieverFragment;
import com.ultraviolet.delieve.view.deliever.list.DeliveryListFragment;
import com.ultraviolet.delieve.view.enroll.BeforeEnrollFragment;
import com.ultraviolet.delieve.view.enroll.EnrollWaitingFragment;
import com.ultraviolet.delieve.view.send.SendFragment;


import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivity extends BaseActivity
        implements FragmentManager.OnBackStackChangedListener  {

    @Inject
    AuthRepository mAuthRepository;

    @Inject
    DeliveryRepository mDeliveryRepository;

    @Inject
    UserRepository mUserRepository;

   @Inject
   EnrollRepository mEnrollService;

    @Inject
    SendFragment mSendFragment;

    @Inject
    BeforeEnrollFragment mBeforeEnrollFragment;

    @Inject
    DeliveryListFragment mDeliveryListFragment;

    @Inject
    DelieverFragment mDelieverFragment;


    @BindView(R.id.navigation)
    BottomNavigationView mBottomNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_send:
                    replaceFragment(mSendFragment);
                    return true;
                case R.id.navigation_deliever:
                    replaceFragment(getDelieverFragment());
                    return true;
                case R.id.navigation_myPage:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        getDiComponent().inject(this);
        getSupportFragmentManager().addOnBackStackChangedListener(this);


        if(mAuthRepository != null){
            /*
            mAuthRepository.getToken("admin", "admin", "password")
                    .subscribe(dto -> {
                        TokenDto authDto = dto;
                        Log.d("credt", authDto.getAccessToken());
                    }, throwable -> {
                        throwable.printStackTrace();
                        Log.d("credt",throwable.getMessage());
                    });*/
/*
            mAuthRepository.register(new UserDto("test", "1234", "cred@naver.com","321", "111111115", "kakao", "fd",
                    "fd","male"))
                    .subscribe(res -> {
                        Log.d("credt2", String.valueOf(res.code()));
                    }, throwable -> {
                        throwable.printStackTrace();
                    });*/
            /*
            mAuthRepository.login("123")
                    .subscribe(dto -> {
                        Log.d("credt", dto.getAccessToken());
                    }, throwable -> {
                        Log.d("credt", throwable.getMessage());
                    });*/
            /*
            mDeliveryRepository.postDeliveryRequest(new DeliveryRequestDto(
                    "HYUNSU ZIP", 129,33,
                    "SEOUL LAND", 111, 39,
                    "2018-05-29T20:05:10.780Z","2018-05-29T20:05:13.002Z",
                    "22", "010-8510-7976",
                    "credtiger", "S", 0.22, 1))
                    .subscribe(res -> {
                        Log.d("credt", String.valueOf(res.message()));
                    }, throwable -> {
                        throwable.printStackTrace();
                    });*/
            /*
            mDeliveryRepository.getDeliveryMatching(37.279173,
                    127.043332,
                    "77")
                    .subscribe(res -> {

                    }, throwable -> {

                    });
                    */
            int w = 300, h = 300;

            Bitmap.Config conf1 = Bitmap.Config.ARGB_8888; // see other conf types
            Bitmap.Config conf2 = Bitmap.Config.ARGB_4444;
            Bitmap bmp1 = Bitmap.createBitmap(w, h, conf1);
            Bitmap bmp2 = Bitmap.createBitmap(w, h, conf2);

            ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
            bmp1.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos1);
            RequestBody body1 = MultipartBody.create(MediaType.parse("image/*"), bos1.toByteArray());

            ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
            bmp2.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos2);
            RequestBody body2 = MultipartBody.create(MediaType.parse("image/*"), bos2.toByteArray());

            RequestBody body3 = MultipartBody.create(MediaType.parse("text/plain"), "test");
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            mEnrollService.requestEnroll(body1, body2,body3)
            .subscribe(res->{
            }, throwable -> {
                throwable.printStackTrace();
            });
        }
        init();
    }

    private void init(){

        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        setupUi();

        //replaceFragment(mSendFragment);
    }

    private void setupUi() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_fragment_container, mSendFragment)
                .commit();
    }

    Fragment getDelieverFragment(){
/*
        switch (mUserRepository.getUserType()){
            case User.USER_DELIEVER :
            break;
            case User.USER_BEFORE_DELIEVER:
            break;
            case User.USER_WAITNG_FOR_JUDGE :
            break;
        }*/

        return mDelieverFragment;
        //return mBeforeEnrollFragment;

    }

    void replaceFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {

        while (getSupportFragmentManager().getBackStackEntryCount()!= 0) {
            getSupportFragmentManager().popBackStackImmediate();
        }
        super.onBackPressed();
    }

    @Override
    public void onBackStackChanged() {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }



}
