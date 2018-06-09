package com.ultraviolet.delieve.view.deliever;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.data.repository.DeliveryRepository;
import com.ultraviolet.delieve.data.repository.UserRepository;
import com.ultraviolet.delieve.model.DeliveryMatchingForDeliever;
import com.ultraviolet.delieve.view.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * matchingStatus bar and navigation/system bar) with user interaction.
 */
public class DelieverWaitingForMatchingActivity extends BaseActivity {

    @Inject
    DeliveryRepository mDeliveryRepository;

    @Inject
    UserRepository mUserRepository;


    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the matchingStatus and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of matchingStatus and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };


    private boolean mVisible;

    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            mDeliveryRepository.delieverFlush(mUserRepository.getUserId()).
                    subscribe(res->{
                        Log.d("credt", "successfully flushed code : " + res.code());
                        subscription.unsubscribe();
                        finish();
                    }, throwable -> {

                    });
            return false;
        }
    };

        Subscription subscription;
        private DeliveryMatchingForDeliever mDeliveryMatchingForDeliever;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_deliever_waiting_for_matching);

            mVisible = true;
            mControlsView = findViewById(R.id.fullscreen_content_controls);
            mContentView = findViewById(R.id.animation_vew);

            getDiComponent().inject(this);

            // Set up the user interaction to manually show or hide the system UI.
            mContentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggle();
                }
            });

            Typeface typeface=Typeface.createFromAsset(getAssets(),"BMJUA.ttf");
            TextView tv=(TextView)findViewById(R.id.tv_wating_deliver);
            tv.setTypeface(typeface);
            // Upon interacting with UI controls, delay any scheduled hide()
            // operations to prevent the jarring behavior of controls going away
            // while interacting with the UI.
            findViewById(R.id.deliever_matching_cancle).setOnTouchListener(mDelayHideTouchListener);

            // Timer
            subscription = Observable.interval(0, 5000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        public void call(Long aLong) {
                            mDeliveryRepository.getDeliveryMatchingForDeliever(37.284377,
                                    127.044373,
                                    String.valueOf(mUserRepository.getUserId()))
                                    .subscribe(res -> {
                                        if (res.code() == 200) {

                                            finishActivity(1);
                                            Intent intent = new Intent(getApplicationContext(), DelieverMatchedDialogActivity.class);
                                            mDeliveryMatchingForDeliever = new DeliveryMatchingForDeliever(res.body());
                                            intent.putExtra("Matching", mDeliveryMatchingForDeliever);
                                            startActivityForResult(intent, 1);
                                        } else {
                                            Log.d("credt", "" + res.code());

                                        }
                                    }, throwable -> {
                                        throwable.printStackTrace();
                                    });
                        }
                    });
        }

        @Override
        protected void onPostCreate(Bundle savedInstanceState) {
            super.onPostCreate(savedInstanceState);

            // Trigger the initial hide() shortly after the activity has been
            // created, to briefly hint to the user that UI controls
            // are available.
            delayedHide(100);
        }

        private void toggle() {
            if (mVisible) {
                hide();
            } else {
                show();
            }
        }

        private void hide() {
            // Hide UI first
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
            mControlsView.setVisibility(View.GONE);
            mVisible = false;

            // Schedule a runnable to remove the matchingStatus and navigation bar after a delay
            mHideHandler.removeCallbacks(mShowPart2Runnable);
            mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
        }

        @SuppressLint("InlinedApi")
        private void show() {
            // Show the system bar
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            mVisible = true;

            // Schedule a runnable to display UI elements after a delay
            mHideHandler.removeCallbacks(mHidePart2Runnable);
            mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
        }

        /**
         * Schedules a call to hide() in delay milliseconds, canceling any
         * previously scheduled calls.
         */
        private void delayedHide(int delayMillis) {
            mHideHandler.removeCallbacks(mHideRunnable);
            mHideHandler.postDelayed(mHideRunnable, delayMillis);
        }

        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(getApplicationContext(), DelieverMatchedActivity.class);
                intent.putExtra("Matching", mDeliveryMatchingForDeliever);
                startActivity(intent);
                finish();
            }
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            subscription.unsubscribe();
        }
}

