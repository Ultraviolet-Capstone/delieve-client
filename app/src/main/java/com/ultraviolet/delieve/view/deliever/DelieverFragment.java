package com.ultraviolet.delieve.view.deliever;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ultraviolet.delieve.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DelieverFragment extends Fragment {


    @OnClick(R.id.start_delieve_btn)
    void onClick(){
        Intent intent = new Intent(getActivity(), DelieverWaitingForMatchingActivity.class);
        startActivity(intent);
    }
    public DelieverFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.fragment_deliever, container, false);

        ButterKnife.bind(this, rootView);

       return rootView;
    }

}
