package com.ultraviolet.delieve.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.data.dto.DeliveryMatchingDto;
import com.ultraviolet.delieve.data.repository.AuthRepository;
import com.ultraviolet.delieve.data.repository.DeliveryRepository;
import com.ultraviolet.delieve.data.repository.EnrollRepository;
import com.ultraviolet.delieve.data.repository.QRApiRepository;
import com.ultraviolet.delieve.data.repository.UserInfoRepository;
import com.ultraviolet.delieve.data.repository.UserRepository;

import com.ultraviolet.delieve.firebase.MyFirebaseInstanceIDService;
import com.ultraviolet.delieve.model.DeliveryMatching;
import com.ultraviolet.delieve.model.User;
import com.ultraviolet.delieve.service.GPSService;
import com.ultraviolet.delieve.view.base.BaseActivity;
import com.ultraviolet.delieve.view.deliever.DelieverFragment;
import com.ultraviolet.delieve.view.deliever.DelieverMatchedActivity;
import com.ultraviolet.delieve.view.mypage.matchingList.MatchingListFragment;
import com.ultraviolet.delieve.view.enroll.BeforeEnrollFragment;
import com.ultraviolet.delieve.view.enroll.EnrollWaitingFragment;
import com.ultraviolet.delieve.view.mypage.MyPageFragment;
import com.ultraviolet.delieve.view.send.SendFragment;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements FragmentManager.OnBackStackChangedListener  {

    private final int ENROLL_ACTIVITY_CODE = 90;
    private final long FINISH_INTERVAL_TIME=2000;
    private long backPressedTime=0;

    @Inject
    UserInfoRepository mUserInfoRepository;

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
    MatchingListFragment mDeliveryListFragment;

    @Inject
    DelieverFragment mDelieverFragment;

    @Inject
    EnrollWaitingFragment mEnrollWaitingFragment;

    @Inject
    MyPageFragment mMyPageFragment;


    @BindView(R.id.navigation)
    BottomNavigationView mBottomNavigationView;

    boolean notification=false;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_send:
                    replaceFragment(mSendFragment);
                    return true;
                case R.id.navigation_deliever:
                    mUserInfoRepository.fetchUserDelivable(mUserRepository.getUserId())
                            .subscribe(res->{
                              mUserRepository.setUserType(res.body().status);

                            }, throwable -> {

                            });
                    replaceFragment(getDelieverFragment());
                    return true;
                case R.id.navigation_myPage:
                    replaceFragment(mMyPageFragment);
                    // TODO(dogfooter): logout 임시
                    /*
                    UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                        @Override
                        public void onCompleteLogout() {
                            getSharedPreferences("auto", Activity.MODE_PRIVATE).edit()
                                    .remove("accessToken").commit();
                            finish();
                        }
                    });*/
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("session", "MainActivity.onCreate");

        String kakaoId=getIntent().getStringExtra("kakaoId");
        ButterKnife.bind(this);
        getDiComponent().inject(this);
        getSupportFragmentManager().addOnBackStackChangedListener(this);

        Bundle bundle = new Bundle();
        bundle.putString("kakaoId", kakaoId);

      //  mSendFragment.setArguments(bundle);
        Log.d("firebaseToken",FirebaseInstanceId.getInstance().getToken().toString());

        mSendFragment.setArguments(bundle);
        checkUserMatched();
        init();

        startService(new Intent(this, GPSService.class));

    }

    private void checkUserMatched() {
        mDeliveryRepository.getDeliveryMatchingListForDeliever(mUserRepository.getUserId())
                .subscribe(res->{
                    DeliveryMatching matching = null;
                    for (DeliveryMatchingDto i : res.body()){
                        Log.d("credt", "matching status : " + i.matchingStatus);
                        if(i.matchingStatus.equals("READY") || i.matchingStatus.equals("PROGRESS")){
                            matching = new DeliveryMatching(i);
                            break;
                        }
                    }
                    if (matching != null){
                        mUserRepository.setUserMatching(matching.matchingId);
                    }

                }, throwable -> {

                });
    }

    private void init(){

        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        setupUi();

        //replaceFragment(mSendFragment);
    }

    private void setupUi() {
        /*notification=getIntent().getBooleanExtra("noti", false);
        Log.d("firebaseInent", String.valueOf(notification));
        if(notification==false){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_fragment_container, mSendFragment)
                    .commit();
        }
        else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_fragment_container, mDelieverFragment)
                    .commit();
        }*/
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_fragment_container, mSendFragment)
                .commit();

    }

    Fragment getDelieverFragment(){
        Fragment targetFragment;
        switch (mUserRepository.getUserType()){
            case User.USER_DELIEVER :
                targetFragment = mDelieverFragment;
            break;
            case User.USER_BEFORE_DELIEVER:
                targetFragment = mBeforeEnrollFragment;
            break;
            case User.USER_WAITNG_FOR_JUDGE :
                targetFragment = mEnrollWaitingFragment;
            break;
            default:
                targetFragment = mBeforeEnrollFragment;
        }

        Log.d("credt", "user state : " + mUserRepository.getUserType());
        return targetFragment;
    }

    void replaceFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, fragment)
                .addToBackStack(null)
                .commit();
        Log.d("session", "MainActivity.replaceFragment to :" + fragment.toString());
    }

    @Override
    public void onBackPressed() {
        long tempTime=System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            while (getSupportFragmentManager().getBackStackEntryCount()!= 0) {
                getSupportFragmentManager().popBackStackImmediate();
            }
            super.onBackPressed();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 뒤로가기 누르면 꺼집니다.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackStackChanged() {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("session", "adfjalksdjflajsdklfjlaksdj");
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("session", "MainActivity.onActivityResult, Reseult CODE : " + resultCode);
        if (requestCode == ENROLL_ACTIVITY_CODE){
            if(resultCode == RESULT_OK){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_fragment_container,mEnrollWaitingFragment)
                        .commit();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("session", "MainActivity.onDestory");
    }
}
