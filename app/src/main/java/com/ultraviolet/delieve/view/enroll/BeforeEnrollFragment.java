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

public class BeforeEnrollFragment extends BaseFragment {

    private final int ENROLL_ACTIVITY_CODE = 90;
    private View rootView;

    @Inject
    EnrollWaitingFragment mEnrollWaitingFragment;

    @OnClick(R.id.be_a_deliever_button)
    void onClick(){
        Intent intent = new Intent(getActivity(), EvaluateDeliver1.class);
        startActivityForResult(intent, ENROLL_ACTIVITY_CODE);
    }

    public BeforeEnrollFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_before_enroll, container, false);
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ENROLL_ACTIVITY_CODE){
            if(resultCode == 1){
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_fragment_container,mEnrollWaitingFragment)
                        .commit();
            }
        }
    }
}
