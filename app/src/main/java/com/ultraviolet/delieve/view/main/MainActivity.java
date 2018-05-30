package com.ultraviolet.delieve.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.data.dto.DelieveryRequestDto;
import com.ultraviolet.delieve.data.repository.AuthRepository;
import com.ultraviolet.delieve.data.repository.DeliveryRequestRepository;
import com.ultraviolet.delieve.data.repository.UserRepository;
import com.ultraviolet.delieve.view.base.BaseActivity;
import com.ultraviolet.delieve.view.deliever.DeliveryListFragment;
import com.ultraviolet.delieve.view.enroll.BeforeEnrollFragment;
import com.ultraviolet.delieve.view.send.SendFragment;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements FragmentManager.OnBackStackChangedListener  {

    @Inject
    AuthRepository mAuthRepository;

    @Inject
    DeliveryRequestRepository mDeliveryRequestRepository;

    @Inject
    UserRepository mUserRepository;

    @Inject
    SendFragment mSendFragment;

    @Inject
    BeforeEnrollFragment mBeforeEnrollFragment;

    @Inject
    DeliveryListFragment mDeliveryListFragment;

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
            mDeliveryRequestRepository.postRequest(new DelieveryRequestDto(
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

        }
        init();
    }

    private void init(){

        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        replaceFragment(mSendFragment);
    }

    Fragment getDelieverFragment(){
/*
        switch (mUserRepository.getUserType()){
            case User.USER_DELIEVER :
            case User.USER_BEFORE_DELIEVER:
            case User.USER_WAITNG_FOR_JUDGE :
        }*/

        return mBeforeEnrollFragment;

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
        /*
        while (getSupportFragmentManager().getBackStackEntryCount()!= 0) {
            getSupportFragmentManager().popBackStackImmediate();
        }*/
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
