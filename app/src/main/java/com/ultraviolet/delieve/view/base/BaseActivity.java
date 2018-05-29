package com.ultraviolet.delieve.view.base;


import android.support.v7.app.AppCompatActivity;

import com.ultraviolet.delieve.MainApplication;
import com.ultraviolet.delieve.dagger.DiComponent;

public abstract class BaseActivity extends AppCompatActivity {

    private MainApplication getMainApplication() {
        return (MainApplication) getApplication();
    }

    protected DiComponent getDiComponent() {
        return getMainApplication().getDiComponent();
    }

}
