package com.ultraviolet.delieve.view.enroll;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.view.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BeforeEnrollFragment extends BaseFragment {

    @OnClick(R.id.be_a_deliever_button)
    void onClick(){
        Intent intent = new Intent(getActivity(), EvaluateDeliver1.class);
        startActivity(intent);
    }

    public BeforeEnrollFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_before_enroll, container, false);
        return rootView;
    }
}
