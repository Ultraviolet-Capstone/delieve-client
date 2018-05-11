package com.ultraviolet.delieve.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.ultraviolet.delieve.R;



public class SendFragment extends Fragment {

    SlidingUpPanelLayout slidingUpPanelLayout;

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        slidingUpPanelLayout = getView().findViewById(R.id.sliding_layout);
        if (slidingUpPanelLayout != null) {
            slidingUpPanelLayout.setPanelHeight(
                    slidingUpPanelLayout.getPanelHeight()+
                            //getActivity().findViewById(R.id.navigation).getHeight()
                    147     // height of navigation bar
            );
            slidingUpPanelLayout.invalidate();
        }
    }
}