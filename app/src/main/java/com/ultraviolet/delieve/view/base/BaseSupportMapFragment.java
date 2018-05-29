package com.ultraviolet.delieve.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.android.gms.maps.SupportMapFragment;
import com.ultraviolet.delieve.MainApplication;
import com.ultraviolet.delieve.dagger.DiComponent;

import butterknife.ButterKnife;

public class BaseSupportMapFragment extends SupportMapFragment {

    private MainApplication getMainApplication() {
        return (MainApplication) getActivity().getApplication();
    }

    protected DiComponent getDiComponent() {
        return getMainApplication().getDiComponent();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }
}

