package com.ultraviolet.delieve.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.ultraviolet.delieve.MainApplication;
import com.ultraviolet.delieve.dagger.DiComponent;

import butterknife.ButterKnife;

public class BaseDialogFragment extends DialogFragment {
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
