package com.ultraviolet.delieve.view.enroll;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.view.base.BaseFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class BeforeEnrollFragment extends BaseFragment {

    private final int ENROLL_ACTIVITY_CODE = 90;
    private View rootView;

    Intent mainIntent;

    @Inject
    EnrollWaitingFragment mEnrollWaitingFragment;

    @OnClick(R.id.be_a_deliever_button)
    void onClick(){
        mainIntent = new Intent(getContext(), EvaluateDeliver1.class);
        Log.d("session", "BeforeEnroleFrag before start Activity:" );
        startActivityForResult(mainIntent, ENROLL_ACTIVITY_CODE);
        Log.d("session", "BeforeEnroleFrag after start Activity:" );
    }

    public BeforeEnrollFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_before_enroll, container, false);
        Log.d("session", "BeforeEnroleFrag getActivity:" + getActivity().toString());

        return rootView;
    }

}
