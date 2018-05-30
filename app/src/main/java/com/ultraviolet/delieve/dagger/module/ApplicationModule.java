package com.ultraviolet.delieve.dagger.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ultraviolet.delieve.MainApplication;
import com.ultraviolet.delieve.model.DeliveryRequest;
import com.ultraviolet.delieve.view.deliever.DelieverFragment;
import com.ultraviolet.delieve.view.deliever.list.DeliveryListFragment;
import com.ultraviolet.delieve.view.deliever.list.DeliveryListPresenter;
import com.ultraviolet.delieve.view.enroll.BeforeEnrollFragment;
import com.ultraviolet.delieve.view.send.SendFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private MainApplication mMainApplication;
    private SendFragment mSendFragment;
    private BeforeEnrollFragment mBeforeEnrollFragment;

    private DeliveryListFragment mDeliveryListFragment;
    private DeliveryListPresenter mDeliveryListPresenter;
    private DelieverFragment mDelieverFragment;

    public ApplicationModule(MainApplication mainApplication) {
        this.mMainApplication = mainApplication;
        mSendFragment = new SendFragment();
        mBeforeEnrollFragment = new BeforeEnrollFragment();

        mDeliveryListFragment = new DeliveryListFragment();
        mDeliveryListPresenter = new DeliveryListPresenter();
        mDelieverFragment = new DelieverFragment();

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
    DeliveryListFragment provideDeliveryListFragment() {return mDeliveryListFragment; }

    @Provides
    @Singleton
    DeliveryListPresenter provideDeliveryListPresenter() { return mDeliveryListPresenter;}

    @Provides
    @Singleton
    DelieverFragment provideDelieverFragment() {return mDelieverFragment; }

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

