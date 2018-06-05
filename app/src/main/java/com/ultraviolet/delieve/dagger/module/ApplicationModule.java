package com.ultraviolet.delieve.dagger.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ultraviolet.delieve.MainApplication;
import com.ultraviolet.delieve.view.deliever.DelieverFragment;
import com.ultraviolet.delieve.view.mypage.matchingList.MatchingListFragment;
import com.ultraviolet.delieve.view.mypage.matchingList.MatchingListPresenter;
import com.ultraviolet.delieve.view.enroll.BeforeEnrollFragment;
import com.ultraviolet.delieve.view.enroll.EnrollWaitingFragment;
import com.ultraviolet.delieve.view.mypage.MyPageFragment;
import com.ultraviolet.delieve.view.send.SendFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private MainApplication mMainApplication;
    private SendFragment mSendFragment;
    private BeforeEnrollFragment mBeforeEnrollFragment;

    private MatchingListFragment mDeliveryListFragment;
    private MatchingListPresenter mMatchingListPresenter;
    private DelieverFragment mDelieverFragment;
    private EnrollWaitingFragment mEnrollWaitingFragment;
    private MyPageFragment mMyPageFragment;


    public ApplicationModule(MainApplication mainApplication) {
        this.mMainApplication = mainApplication;
        mSendFragment = new SendFragment();
        mBeforeEnrollFragment = new BeforeEnrollFragment();

        mDeliveryListFragment = new MatchingListFragment();
        mMatchingListPresenter = new MatchingListPresenter();
        mDelieverFragment = new DelieverFragment();
        mEnrollWaitingFragment = new EnrollWaitingFragment();
        mMyPageFragment = new MyPageFragment();

    }

    @Provides
    @Singleton
    MainApplication provideMainApplication() {
        return mMainApplication;
    }

    @Provides
    @Singleton
    SendFragment provideSendFragment() {return mSendFragment; }

    @Provides
    @Singleton
    BeforeEnrollFragment provideBeforeEnrollFragment() {return mBeforeEnrollFragment; }

    @Provides
    @Singleton
    MatchingListFragment provideDeliveryListFragment() {return mDeliveryListFragment; }

    @Provides
    @Singleton
    MatchingListPresenter provideDeliveryListPresenter() { return mMatchingListPresenter;}

    @Provides
    @Singleton
    DelieverFragment provideDelieverFragment() {return mDelieverFragment; }

    @Provides
    @Singleton
    EnrollWaitingFragment provideEnrollWaitingFragment() { return mEnrollWaitingFragment; }

    @Provides
    @Singleton
    MyPageFragment provideMyPageFragment() {return mMyPageFragment; }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(MainApplication mainApplication) {
        return PreferenceManager.getDefaultSharedPreferences(mainApplication);
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mMainApplication;
    }
}

